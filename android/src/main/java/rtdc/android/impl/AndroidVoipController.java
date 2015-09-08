package rtdc.android.impl;

import android.content.Intent;
import org.linphone.BandwidthManager;
import org.linphone.LinphoneManager;
import org.linphone.core.*;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.presenter.CommunicationHubInCallActivity;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Config;
import rtdc.core.impl.VoipController;
import rtdc.core.model.User;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidVoipController implements VoipController{

    private static final AndroidVoipController INST = new AndroidVoipController();

    private static User currentRegisteredUser;
    private static LinphoneAuthInfo currentAuthInfo;
    private static LinphoneProxyConfig currentProxyConfig;

    private AndroidVoipController(){}

    public static AndroidVoipController get(){ return INST; }

    @Override
    public void registerUser(User user) {
        try {
            LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
            String sipAddress = "sip:" + user.getUsername() + "@" + Config.ASTERISK_IP;
            //LinphoneAddress address = LinphoneCoreFactory.instance().createLinphoneAddress(sipAddress);

            // TODO: Fetch the password for the user and use it to register
            currentAuthInfo = LinphoneCoreFactory.instance().createAuthInfo(user.getUsername(), "password", null, "sip:" + Config.ASTERISK_IP);
            LiblinphoneThread.get().getLinphoneCore().addAuthInfo(currentAuthInfo);

            currentProxyConfig = lc.createProxyConfig(sipAddress, Config.ASTERISK_IP, null, true);
            currentProxyConfig.setExpires(60);
            lc.addProxyConfig(currentProxyConfig);
            currentRegisteredUser = user;
        } catch (LinphoneCoreException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unregisterCurrentUser() {
        LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
        lc.removeAuthInfo(currentAuthInfo);
        lc.removeProxyConfig(currentProxyConfig);
        currentAuthInfo = null;
        currentProxyConfig = null;
        currentRegisteredUser = null;
    }

    @Override
    public void call(User user) {
        try {
            String sipAddress = "sip:" + user.getId() + "@" + Config.ASTERISK_IP;
            LinphoneCall call = LiblinphoneThread.get().getLinphoneCore().invite(sipAddress);

            if (call == null)
                Logger.getLogger(LiblinphoneThread.class.getName()).log(Level.WARNING, "Could not place call to " + sipAddress + ", aborting...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setMicMuted(boolean mute) {
        LiblinphoneThread.get().getLinphoneCore().muteMic(mute);
    }

    @Override
    public void setSpeaker(boolean enabled) {
        LiblinphoneThread.get().getLinphoneCore().muteMic(enabled);
    }

    @Override
    public void setVideo(boolean enabled) {
        //LinphoneManager.getInstance().addVideo();
        //LiblinphoneThread.get().getLinphoneCore().enableVideo(enabled, enabled);

        LinphoneCall call = LiblinphoneThread.get().getLinphoneCore().getCurrentCall();
        if(call != null) {
            call.enableCamera(true);
            /*if(this.mServiceContext.getResources().getBoolean(2131361854)) {
                LinphoneService.instance().refreshIncallIcon(this.mLc.getCurrentCall());
            }*/
        }

        LinphoneCore lc = LiblinphoneThread.get().getLinphoneCore();
        LinphoneCall lCall = lc.getCurrentCall();
        if(lCall == null) {
            System.out.println("Nope null");
            //Log.e(new Object[]{"Trying to reinviteWithVideo while not in call: doing nothing"});
            //return false;
        } else {
            LinphoneCallParams params = lCall.getCurrentParamsCopy();
            if(params.getVideoEnabled()) {
                //return false;
            } else {
                BandwidthManager.getInstance().updateWithProfileSettings(lc, params);
                if(!params.getVideoEnabled()) {
                    //return false;
                } else {
                    lc.updateCall(lCall, params);
                    //return true;
                }
            }
        }
        LiblinphoneThread.get().getLinphoneCore().enableVideo(enabled, enabled);
        //LiblinphoneThread.get().getLinphoneCore().setVideoCodecs();
        for(int i = 0; i < LiblinphoneThread.get().getLinphoneCore().getVideoCodecs().length; i++)
            Logger.getLogger(AndroidVoipController.class.getName()).log(Level.INFO, LiblinphoneThread.get().getLinphoneCore().getVideoCodecs()[i].getMime());
        //return reinviteWithVideo();
    }

    @Override
    public void acceptCall() {
        try {
            LiblinphoneThread.get().getLinphoneCore().acceptCall(LiblinphoneThread.get().getCurrentCall());
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
    public void hangup() {
        LiblinphoneThread.get().getLinphoneCore().terminateCall(LiblinphoneThread.get().getCurrentCall());
    }
}
