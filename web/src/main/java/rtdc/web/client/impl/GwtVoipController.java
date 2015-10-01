package rtdc.web.client.impl;

import rtdc.core.impl.VoipController;
import rtdc.core.model.User;

/**
 * Created by Jonathan on 2015-07-26.
 */
public class GwtVoipController implements VoipController{

    private static final GwtVoipController INST = new GwtVoipController();

    public static GwtVoipController get(){
        return INST;
    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public void unregisterCurrentUser() {

    }

    @Override
    public void call(User user, boolean videoEnabled) {

    }

    @Override
    public void setMicMuted(boolean mute) {

    }

    public void setSpeaker(boolean enabled){

    }

    @Override
    public void setVideo(boolean enabled) {

    }

    @Override
    public boolean isVideoEnabled() {
        return false;
    }

    @Override
    public void setRemoteVideo(boolean enabled) {

    }

    @Override
    public boolean isMicMuted() {
        return false;
    }

    @Override
    public boolean isSpeakerEnabled() {
        return false;
    }

    @Override
    public boolean isReceivingRemoteVideo() {
        return false;
    }

    @Override
    public void acceptCall() {

    }

    @Override
    public void declineCall() {

    }

    @Override
    public void hangup() {

    }
}
