package rtdc.android.voip;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import org.linphone.core.*;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.presenter.CommunicationHubReceivingCallActivity;
import rtdc.android.presenter.MainActivity;
import rtdc.android.presenter.fragments.CommunicationHubFragment;
import rtdc.android.presenter.fragments.VideoCallFragment;
import rtdc.core.Config;
import rtdc.core.Session;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUserEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Message;
import rtdc.core.model.User;
import rtdc.core.service.Service;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LiblinphoneThread extends Thread implements LinphoneCoreListener{

    private LinphoneCore lc;
    private LinphoneCall currentCall;
    private LinphoneAddress currentCallRemoteAddress;
    private boolean running;

    // We decrease this value each time there's a missed call so that each missed call as a unique notification ID
    private int MISSED_CALL_NOTIFICATION_ID = Integer.MAX_VALUE;

    private static final LiblinphoneThread INST = new LiblinphoneThread();

    private LiblinphoneThread(){
        try {
            lc = LinphoneCoreFactory.instance().createLinphoneCore(this, AndroidBootstrapper.getAppContext());
            lc.setPreferredVideoSize(VideoSize.VIDEO_SIZE_VGA);
            start();
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    public static LiblinphoneThread get(){
        return INST;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            lc.iterate();
            try {
                Thread.sleep(50);
            } catch (InterruptedException ie) {
                Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.SEVERE, "Interrupted! Aborting...");
                return;
            }
        }
        Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.INFO, "Shutting down...");
        lc.destroy();
        Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.INFO, "Exited");
    }

    public LinphoneCore getLinphoneCore(){
        return lc;
    }

    public LinphoneCall getCurrentCall(){
        return currentCall;
    }

    public void setCurrentCallRemoteAddress(LinphoneAddress address){
        currentCallRemoteAddress = address;
    }

    public LinphoneAddress getCurrentCallRemoteAddress(){
        return currentCallRemoteAddress;
    }

    @Override
    public void callState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state, String s) {
        Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.INFO, state + "");
        if(state == LinphoneCall.State.IncomingReceived){
            // Only accept the incoming call if we're not currently in a call

            if(currentCall != null){
                addMissedCallNotification(linphoneCall.getRemoteAddress().getDisplayName());
                lc.declineCall(linphoneCall, Reason.Busy);
                return;
            }

            // We just received a call, interrupt everything and display the incoming call view

            currentCall = linphoneCall;
            currentCallRemoteAddress = linphoneCall.getRemoteAddress();
            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), CommunicationHubReceivingCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);
        }else if(state == LinphoneCall.State.OutgoingProgress){
            currentCall = linphoneCall;
            // We don't want to set the currentCallRemoteAddress value here because Asterisk doesn't send the actual remote
            // user name when you invite someone into a call, no idea why
            //currentCallRemoteAddress = linphoneCall.getRemoteAddress();

            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), CommunicationHubInCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);
        }else if(state == LinphoneCall.State.Connected){
            // If we're in the In Call activity, we need to update the interface

            if(CommunicationHubInCallActivity.getCurrentInstance() != null)
                CommunicationHubInCallActivity.getCurrentInstance().onCallEstablished();
        }else if(state == LinphoneCall.State.CallEnd || state == LinphoneCall.State.Error || state == LinphoneCall.State.CallReleased){
            Context context = AndroidBootstrapper.getAppContext();

            // Remove the notification for the call

            ((NotificationManager) context.getSystemService(
                    context.NOTIFICATION_SERVICE)).cancel(CommunicationHubInCallActivity.IN_CALL_NOTIFICATION_ID);

            // If the current activity is the In Call interface, we clean it up

            if(CommunicationHubInCallActivity.isActivityVisible() && linphoneCall == currentCall)
                CommunicationHubInCallActivity.getCurrentInstance().onCallHangup();

            // If the current activity is the Incoming Call interface, we simply remove it

            if(CommunicationHubReceivingCallActivity.isActivityVisible())
                CommunicationHubReceivingCallActivity.getInstance().finish();

            Reason reason = linphoneCall.getErrorInfo().getReason();
            String fromUserName = linphoneCall.getCallLog().getFrom().getUserName();


            if(state == LinphoneCall.State.CallReleased && fromUserName.equals(Session.getCurrentSession().getUser().getUsername())){
                if(reason == Reason.IOError){
                    // Tried to call a user that is not logged in

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            final Message message = new Message();
                            message.setSender(Session.getCurrentSession().getUser());
                            message.setTimeSent(new Date());
                            message.setStatus(Message.Status.read);
                            message.setReceiver(event.getUser());
                            message.setContent(Config.COMMAND_EXEC_KEY + "Missed call");
                            Service.saveOrUpdateMessage(message);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(currentCallRemoteAddress.getUserName());
                }
            }

            if(state == LinphoneCall.State.CallReleased && !fromUserName.equals(Session.getCurrentSession().getUser().getUsername())){
                final Message message = new Message();
                message.setReceiver(Session.getCurrentSession().getUser());
                message.setTimeSent(new Date());
                message.setStatus(Message.Status.read);
                if(reason == Reason.NotAnswered){
                    // Add a notification for the user to let it know it missed a call
                    addMissedCallNotification(linphoneCall.getRemoteAddress().getDisplayName());

                    // Save a message on the server for the missed call

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            message.setSender(event.getUser());
                            message.setContent(Config.COMMAND_EXEC_KEY + "Missed call");
                            Service.saveOrUpdateMessage(message);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(currentCallRemoteAddress.getUserName());
                }else if(reason == Reason.Declined || reason == Reason.Busy){
                    // Save a message on the server for the declined call

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            message.setSender(event.getUser());
                            message.setContent(Config.COMMAND_EXEC_KEY + "Call rejected");
                            Service.saveOrUpdateMessage(message);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(currentCallRemoteAddress.getUserName());
                }else if(reason == Reason.None){
                    // Save a message on the server for the ending of the call

                    final String minutes = String.format("%02d", linphoneCall.getDuration() / 60);
                    final String seconds = String.format("%02d", linphoneCall.getDuration() % 60);

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            message.setSender(event.getUser());
                            message.setContent(Config.COMMAND_EXEC_KEY + "Call ended, duration " + minutes + ":" + seconds);
                            Service.saveOrUpdateMessage(message);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(currentCallRemoteAddress.getUserName());
                }
            }

            currentCall = null;
        }
    }

    private void addMissedCallNotification(String missedCaller){
        Context context = AndroidBootstrapper.getAppContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_phone_missed_white_24dp)
                        .setContentTitle("RTDC")
                        .setContentText("Missed call from " + missedCaller);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("fragment", 2);
        PendingIntent inCallPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(inCallPendingIntent);
        mBuilder.setAutoCancel(true);
        ((NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE)).notify(MISSED_CALL_NOTIFICATION_ID--, mBuilder.build());
    }

    @Override
    public void messageReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom, final LinphoneChatMessage linphoneChatMessage) {
        Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.INFO, "Message received: " + linphoneChatMessage.getText());

        if(linphoneChatMessage.getText().startsWith(Config.COMMAND_EXEC_KEY + "Video: ")){
            // Check to make sure that if we are in a call that the one that sent the message is the one we're in a call with
            // (It could be someone that's trying to request a video call, but we're in a call with someone already)
            if(currentCall != null && !currentCallRemoteAddress.getUserName().equals(linphoneChatMessage.getFrom().getUserName()))
                return;
            // There was an update regarding the video of the call
            boolean video = Boolean.valueOf(linphoneChatMessage.getText().replace(Config.COMMAND_EXEC_KEY + "Video: ", ""));
            AndroidVoipController.get().setRemoteVideo(video);
            if(video){
                if(AndroidVoipController.get().isVideoEnabled()){
                    // Remote video is on and we're already in the video fragment. Make sure the pause screen is off
                    CommunicationHubInCallActivity.getCurrentInstance().displayPauseVideoStatus(false);
                }else{
                    // Remote video is on and we're not in the video fragment. Go to the video fragment
                    if(CommunicationHubInCallActivity.getCurrentInstance() != null)
                        CommunicationHubInCallActivity.getCurrentInstance().displayVideo();
                    else if(CommunicationHubReceivingCallActivity.getInstance() != null)
                        ((TextView)CommunicationHubReceivingCallActivity.getInstance().findViewById(R.id.incomingCallText)).setText("Incoming video call");
                }
            }else{
                if(AndroidVoipController.get().isVideoEnabled()){
                    // Remote video is off and and we're broadcasting video. Pause the video call
                    CommunicationHubInCallActivity.getCurrentInstance().displayPauseVideoStatus(true);
                }else{
                    if(CommunicationHubInCallActivity.getCurrentInstance() != null && CommunicationHubInCallActivity.getCurrentInstance().getCurrentFragment() instanceof VideoCallFragment) {
                        // Remote video is off and and we're in the video fragment. No point in staying there, go to audio fragment
                        CommunicationHubInCallActivity.getCurrentInstance().displayAudio();
                    }else if(CommunicationHubReceivingCallActivity.getInstance() != null)
                        ((TextView)CommunicationHubReceivingCallActivity.getInstance().findViewById(R.id.incomingCallText)).setText("Incoming call");
                }
            }
        }else{
            if(CommunicationHubFragment.getInstance() != null){
                JSONObject object = new JSONObject(linphoneChatMessage.getText());
                Message message = new Message(object);
                if(CommunicationHubFragment.getInstance().getMessagingUser().getId() == message.getSenderID()) {
                    message.setStatus(Message.Status.read);
                    CommunicationHubFragment.getInstance().addMessage(message);
                }else{
                    message.setStatus(Message.Status.delivered);
                    CommunicationHubFragment.getInstance().addRecentContact(message);
                }
                Service.saveOrUpdateMessage(message);
            }
        }
    }

    @Override
    public void authInfoRequested(LinphoneCore linphoneCore, String s, String s1, String s2) {

    }

    @Override
    public void globalState(LinphoneCore linphoneCore, LinphoneCore.GlobalState globalState, String s) {

    }

    @Override
    public void callStatsUpdated(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCallStats linphoneCallStats) {

    }

    @Override
    public void callEncryptionChanged(LinphoneCore linphoneCore, LinphoneCall linphoneCall, boolean b, String s) {

    }

    @Override
    public void registrationState(LinphoneCore linphoneCore, LinphoneProxyConfig linphoneProxyConfig, LinphoneCore.RegistrationState registrationState, String s) {

    }

    @Override
    public void newSubscriptionRequest(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend, String s) {

    }

    @Override
    public void notifyPresenceReceived(LinphoneCore linphoneCore, LinphoneFriend linphoneFriend) {

    }

    @Override
    public void isComposingReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom) {

    }

    @Override
    public void dtmfReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, int i) {

    }

    @Override
    public void ecCalibrationStatus(LinphoneCore linphoneCore, LinphoneCore.EcCalibratorStatus ecCalibratorStatus, int i, Object o) {

    }

    @Override
    public void uploadProgressIndication(LinphoneCore linphoneCore, int i, int i1) {

    }

    @Override
    public void uploadStateChanged(LinphoneCore linphoneCore, LinphoneCore.LogCollectionUploadState logCollectionUploadState, String s) {

    }

    @Override
    public void notifyReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneAddress linphoneAddress, byte[] bytes) {

    }

    @Override
    public void transferState(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneCall.State state) {

    }

    @Override
    public void infoReceived(LinphoneCore linphoneCore, LinphoneCall linphoneCall, LinphoneInfoMessage linphoneInfoMessage) {

    }

    @Override
    public void subscriptionStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, SubscriptionState subscriptionState) {

    }

    @Override
    public void notifyReceived(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, String s, LinphoneContent linphoneContent) {

    }

    @Override
    public void publishStateChanged(LinphoneCore linphoneCore, LinphoneEvent linphoneEvent, PublishState publishState) {

    }

    @Override
    public void configuringStatus(LinphoneCore linphoneCore, LinphoneCore.RemoteProvisioningState remoteProvisioningState, String s) {

    }

    @Override
    public void show(LinphoneCore linphoneCore) {

    }

    @Override
    public void displayStatus(LinphoneCore linphoneCore, String s) {

    }

    @Override
    public void displayMessage(LinphoneCore linphoneCore, String s) {

    }

    @Override
    public void displayWarning(LinphoneCore linphoneCore, String s) {

    }

    @Override
    public void fileTransferProgressIndication(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, int i) {

    }

    @Override
    public void fileTransferRecv(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, byte[] bytes, int i) {

    }

    @Override
    public int fileTransferSend(LinphoneCore linphoneCore, LinphoneChatMessage linphoneChatMessage, LinphoneContent linphoneContent, ByteBuffer byteBuffer, int i) {
        return 0;
    }
}
