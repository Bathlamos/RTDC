package rtdc.android.impl.voip;

import android.content.Intent;
import org.linphone.core.*;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.AndroidNotificationController;
import rtdc.android.presenter.InCallActivity;
import rtdc.android.presenter.IncomingCallActivity;
import rtdc.android.presenter.fragments.MessagesFragment;
import rtdc.core.Config;
import rtdc.core.Session;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUserEvent;
import rtdc.core.impl.voip.*;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Message;
import rtdc.core.service.Service;
import rtdc.core.impl.voip.VoIPManager.Reason;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidVoIPListener extends LinphoneCoreListenerBase implements VoIPListener{

    @Override
    public void callState(LinphoneCore lc, LinphoneCall call, LinphoneCall.State state, String message) {
        callState(new AndroidVoIPManager(lc), new AndroidCall(call), AndroidCall.linphoneStateToState(state), message);
    }

    @Override
    public void callState(VoIPManager voIPManager, Call call, Call.State state, String message) {
        Logger.getLogger(AndroidVoIPListener.class.getName()).log(Level.INFO, state + "");

        for(VoIPListener listener: AndroidVoIPThread.getInstance().getVoIPListeners())
            listener.callState(voIPManager,call,state,message);

        // Incoming Received
        if(state == Call.State.incomingReceived){

            // Only accept the incoming call if we're not currently in a call
            if(AndroidVoIPThread.getInstance().getCall() != null){
                AndroidNotificationController.addMissedCallNotification(call.getRemoteAddress().getDisplayName());
                voIPManager.declineCall(call, Reason.busy);
                return;
            }

            // We just received a call, interrupt everything and display the incoming call view
            AndroidVoIPThread.getInstance().setCall(call);
            AndroidVoIPThread.getInstance().setRemoteAddress(call.getRemoteAddress());
            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), IncomingCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);

        // Call initiated & waiting for response
        }else if(state == Call.State.outgoingProgress){
            AndroidVoIPThread.getInstance().setCall(call);
            // We don't want to set the currentCallRemoteAddress value here because Asterisk doesn't send the actual remote
            // user name when you invite someone into a call, no idea why
            //currentCallRemoteAddress = linphoneCall.getRemoteAddress();

            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), InCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);


            // IF ERROR OR WE HANGUP - RETURN TO PREVIOUS
        }else if(state == Call.State.callEnd || state == Call.State.error || state == Call.State.callReleased){
            Reason reason = call.getReasonForError();
            String fromUsername = call.getFrom().getUsername();


            if(state == Call.State.callReleased && fromUsername.equals(Session.getCurrentSession().getUser().getUsername())){
                if(reason == Reason.ioError){
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
                    Service.getUser(AndroidVoIPThread.getInstance().getRemoteAddress().getUsername());
                }
            }

            if(state == Call.State.callReleased && !fromUsername.equals(Session.getCurrentSession().getUser().getUsername())){
                final Message rtdcMessage = new Message();
                rtdcMessage.setReceiver(Session.getCurrentSession().getUser());
                rtdcMessage.setTimeSent(new Date());
                rtdcMessage.setStatus(Message.Status.read);
                if(reason == Reason.notAnswered){
                    // Add a notification for the user to let it know it missed a call
                    AndroidNotificationController.addMissedCallNotification(call.getRemoteAddress().getDisplayName());

                    // Save a message on the server for the missed call

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            rtdcMessage.setSender(event.getUser());
                            rtdcMessage.setContent(Config.COMMAND_EXEC_KEY + "Missed call");
                            Service.saveOrUpdateMessage(rtdcMessage);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(AndroidVoIPThread.getInstance().getRemoteAddress().getUsername());
                }else if(reason == Reason.declined || reason == Reason.busy){
                    // Save a message on the server for the declined call

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            rtdcMessage.setSender(event.getUser());
                            rtdcMessage.setContent(Config.COMMAND_EXEC_KEY + "Call rejected");
                            Service.saveOrUpdateMessage(rtdcMessage);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(AndroidVoIPThread.getInstance().getRemoteAddress().getUsername());
                }else if(reason == Reason.none){
                    // Save a message on the server for the ending of the call

                    final String minutes = String.format("%02d", call.getDuration() / 60);
                    final String seconds = String.format("%02d", call.getDuration() % 60);

                    FetchUserEvent.Handler handler = new FetchUserEvent.Handler() {
                        @Override
                        public void onUserFetched(FetchUserEvent event) {
                            Event.unsubscribe(FetchUserEvent.TYPE, this);
                            rtdcMessage.setSender(event.getUser());
                            rtdcMessage.setContent(Config.COMMAND_EXEC_KEY + "Call ended, duration " + minutes + ":" + seconds);
                            Service.saveOrUpdateMessage(rtdcMessage);
                        }
                    };
                    Event.subscribe(FetchUserEvent.TYPE, handler);
                    Service.getUser(AndroidVoIPThread.getInstance().getRemoteAddress().getUsername());
                }
            }

            AndroidVoIPThread.getInstance().setCall(null);
        }
    }

    public void messageReceived(LinphoneCore lc, LinphoneChatRoom cr, LinphoneChatMessage message) {
        messageReceived(new AndroidVoIPManager(lc), new AndroidTextGroup(cr), new AndroidTextMessage(message));
    }

    @Override
    public void messageReceived(VoIPManager voIPManager, TextGroup textGroup, TextMessage textMessage) {
        Logger.getLogger(AndroidVoIPThread.class.getName()).log(Level.INFO, "Message received: " + textMessage.getText());

        boolean foundMessageFragment = false;
        for(VoIPListener listener: AndroidVoIPThread.getInstance().getVoIPListeners()) {
            listener.messageReceived(voIPManager, textGroup, textMessage);
            if(listener instanceof MessagesFragment)
                foundMessageFragment = true;
        }

        if(!foundMessageFragment && !textMessage.getText().startsWith(Config.COMMAND_EXEC_KEY + "Video: ")){
            JSONObject object = new JSONObject(textMessage.getText());
            Message message = new Message(object);
            message.setStatus(Message.Status.delivered);
            AndroidNotificationController.addTextMessageNotification(message);
            Service.saveOrUpdateMessage(message);
        }
    }
}
