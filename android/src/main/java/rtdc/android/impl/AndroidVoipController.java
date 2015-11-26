package rtdc.android.impl;

import android.content.Intent;
import org.linphone.BandwidthManager;
import org.linphone.core.*;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.impl.VoipController;
import rtdc.core.model.Message;
import rtdc.core.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidVoipController implements VoipController{

    private static final AndroidVoipController INST = new AndroidVoipController();
    private  static final String ASTERISK_IP = AndroidConfig.getProperty("asterisk_ip");
    private  static final String COMMAND_EXEC_KEY = AndroidConfig.getProperty("command_exec_key");


    private static LinphoneAuthInfo currentAuthInfo;
    private static LinphoneProxyConfig currentProxyConfig;
    private boolean speakerEnabled;
    private boolean micMuted;
    private boolean videoEnabled;
    private boolean remoteVideo;

    private AndroidVoipController(){}

    public static AndroidVoipController get(){ return INST; }

    @Override
    public void registerUser(User user, String password) {
        try {
            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Registering user...");

            LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
            String sipAddress = "sip:" + user.getUsername() + "@" + ASTERISK_IP;
            //LinphoneAddress address = LinphoneCoreFactory.instance().createLinphoneAddress(sipAddress);

            currentAuthInfo = LinphoneCoreFactory.instance().createAuthInfo(user.getUsername(), password, null, "sip:" + ASTERISK_IP);
            LiblinphoneThread.get().getLinphoneCore().addAuthInfo(currentAuthInfo);

            currentProxyConfig = lc.createProxyConfig(sipAddress, ASTERISK_IP, null, true);
            currentProxyConfig.setExpires(60);
            lc.addProxyConfig(currentProxyConfig);

            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Registered user " + user.getId() + " on SIP server");
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterCurrentUser() {
        LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();

        // Set the user to be unregistered

        currentProxyConfig.edit();
        currentProxyConfig.enableRegister(false);
        currentProxyConfig.done();

        // Let the Liblinphone thread unregister

        while(currentProxyConfig.getState() != LinphoneCore.RegistrationState.RegistrationCleared) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Once we've been successfully unregistered, remove all authentication info

        lc.removeAuthInfo(currentAuthInfo);
        lc.removeProxyConfig(currentProxyConfig);
        currentAuthInfo = null;
        currentProxyConfig = null;
        Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Unregistered user from the SIP server");
    }

    @Override
    public void call(User user, boolean videoEnabled) {
        try {
            String sipAddress = "sip:" + user.getId() + "@" + ASTERISK_IP;
            LinphoneAddress lAddress = LiblinphoneThread.get().getLinphoneCore().interpretUrl(sipAddress);
            lAddress.setDisplayName(user.getFirstName() + " " + user.getLastName());

            LinphoneCallParams params = LiblinphoneThread.get().getLinphoneCore().createDefaultCallParameters();
            params.setVideoEnabled(true);

            LinphoneCall call = LiblinphoneThread.get().getLinphoneCore().inviteAddressWithParams(lAddress, params);

            // Need to set the username of the address after the call or else Asterisk will try to initiate the call using the
            // username instead of the user extension, which fails the call

            lAddress.setUserName(user.getUsername());
            LiblinphoneThread.get().setCurrentCallRemoteAddress(lAddress);

            // Reset call options

            setRemoteVideo(false);
            setSpeaker(false);
            setMicMuted(false);
            setVideo(videoEnabled);

            if (call == null)
                Logger.getLogger(AndroidVoipController.class.getName()).log(Level.WARNING, "Could not place call to " + sipAddress + ", aborting...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMicMuted(boolean mute) {
        micMuted = mute;
        LiblinphoneThread.get().getLinphoneCore().muteMic(mute);
    }

    @Override
    public void setSpeaker(boolean enabled) {
        speakerEnabled = enabled;
        LiblinphoneThread.get().getLinphoneCore().enableSpeaker(enabled);
    }

    @Override
    public void setVideo(boolean enabled) {
        LinphoneCore lc =  LiblinphoneThread.get().getLinphoneCore();
        LinphoneCall call = lc.getCurrentCall();

        if (call == null)
            return;

        videoEnabled = enabled;
        call.enableCamera(enabled);

        if(enabled){
            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Enabling video");
            LinphoneCallParams params = call.getCurrentParamsCopy();

            // Check if video possible regarding bandwidth limitations
            BandwidthManager.getInstance().updateWithProfileSettings(LiblinphoneThread.get().getLinphoneCore(), params);

            if (!params.getVideoEnabled()){
                Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Video cannot be enabled: not enough bandwidth");
                return;
            }

            //lc.enableVideo(true, true);

            String sipAddress = LiblinphoneThread.get().getCurrentCallRemoteAddress().asStringUriOnly();
            LinphoneChatMessage m = lc.getOrCreateChatRoom(sipAddress).createLinphoneChatMessage(COMMAND_EXEC_KEY + "Video: true");
            lc.getOrCreateChatRoom(sipAddress).sendChatMessage(m);
        }else{
            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, "Disabling video");

            //lc.enableVideo(false, true);

            String sipAddress = LiblinphoneThread.get().getCurrentCallRemoteAddress().asStringUriOnly();
            LinphoneChatMessage m = lc.getOrCreateChatRoom(sipAddress).createLinphoneChatMessage(COMMAND_EXEC_KEY + "Video: false");
            lc.getOrCreateChatRoom(sipAddress).sendChatMessage(m);
        }
    }

    @Override
    public boolean isMicMuted() { return micMuted; }

    @Override
    public boolean isSpeakerEnabled() { return speakerEnabled; }

    @Override
    public boolean isVideoEnabled(){ return videoEnabled; }

    @Override
    public void setRemoteVideo(boolean enabled) { remoteVideo = enabled; }

    @Override
    public boolean isReceivingRemoteVideo() { return remoteVideo; }

    @Override
    public void acceptCall() {
        try {
            LinphoneCallParams params = LiblinphoneThread.get().getLinphoneCore().createDefaultCallParameters();

            // Reset call options

            setSpeaker(false);
            setMicMuted(false);

            params.setVideoEnabled(true);

            LiblinphoneThread.get().getLinphoneCore().acceptCallWithParams(LiblinphoneThread.get().getCurrentCall(), params);

            setVideo(remoteVideo);

            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), CommunicationHubInCallActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declineCall() {
        LiblinphoneThread.get().getLinphoneCore().declineCall(LiblinphoneThread.get().getCurrentCall(), Reason.Declined);
    }

    @Override
    public void sendMessage(Message message) {
        String sipAddress = "sip:" + message.getReceiver().getUsername() + "@" + ASTERISK_IP;
        LinphoneChatRoom c = LiblinphoneThread.get().getLinphoneCore().getOrCreateChatRoom(sipAddress);
        LinphoneChatMessage m = c.createLinphoneChatMessage(message.toString());
        LiblinphoneThread.get().getLinphoneCore().getOrCreateChatRoom(sipAddress).sendChatMessage(m);
    }

    @Override
    public void hangup() {
        LiblinphoneThread.get().getLinphoneCore().terminateCall(LiblinphoneThread.get().getCurrentCall());
    }
}
