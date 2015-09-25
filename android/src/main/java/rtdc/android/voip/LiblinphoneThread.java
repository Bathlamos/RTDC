package rtdc.android.voip;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import org.linphone.core.*;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.impl.AndroidVoipController;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.presenter.CommunicationHubReceivingCallActivity;
import rtdc.android.presenter.fragments.VideoCallFragment;
import rtdc.core.model.User;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LiblinphoneThread extends Thread implements LinphoneCoreListener{

    private LinphoneCore lc;
    private LinphoneCall currentCall;
    private LinphoneAddress currentCallRemoteAddress;
    private boolean running;

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

            // Reset call options

            AndroidVoipController.get().setVideo(false);
            AndroidVoipController.get().setSpeaker(false);
            AndroidVoipController.get().setMicMuted(false);

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

            if(CommunicationHubInCallActivity.isActivityVisible())
                CommunicationHubInCallActivity.getCurrentInstance().onCallHangup();

            // If the current activity is the Incoming Call interface, we simply remove it

            if(CommunicationHubReceivingCallActivity.isActivityVisible()){
                CommunicationHubReceivingCallActivity.getInstance().finish();
            }
        }
    }

    @Override
    public void messageReceived(LinphoneCore linphoneCore, LinphoneChatRoom linphoneChatRoom, LinphoneChatMessage linphoneChatMessage) {
        Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.INFO, "Message received: " + linphoneChatMessage.getText());

        if(linphoneChatMessage.getText().startsWith("Video: ")){
            // There was an update regarding the video of the call
            boolean video = Boolean.valueOf(linphoneChatMessage.getText().replace("Video: ", ""));
            AndroidVoipController.get().setRemoteVideo(video);
            if(video){
                if(AndroidVoipController.get().isVideoEnabled()){
                    // Remote video is on and we're already in the video fragment. Make sure the pause screen is off
                    CommunicationHubInCallActivity.getCurrentInstance().displayPauseVideoStatus(false);
                }else{
                    // Remote video is on and we're not in the video fragment. Go to the video fragment
                    CommunicationHubInCallActivity.getCurrentInstance().displayVideo();
                }
            }else{
                if(AndroidVoipController.get().isVideoEnabled()){
                    // Remote video is off and and we're broadcasting video. Pause the video call
                    CommunicationHubInCallActivity.getCurrentInstance().displayPauseVideoStatus(true);
                }else{
                    if(CommunicationHubInCallActivity.getCurrentInstance().getCurrentFragment() instanceof VideoCallFragment) {
                        // Remote video is off and and we're in the video fragment. No point in staying there, go to audio fragment
                        CommunicationHubInCallActivity.getCurrentInstance().displayAudio();
                    }
                }
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
