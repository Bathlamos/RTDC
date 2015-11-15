package rtdc.core.impl;

import rtdc.core.model.Message;
import rtdc.core.model.User;

public interface VoipController {

    public void registerUser(User user, String password);
    public void unregisterCurrentUser();

    public void call(User user, boolean videoEnabled);
    public void acceptCall();
    public void declineCall();
    public void sendMessage(Message message);

    public void setMicMuted(boolean mute);
    public void setSpeaker(boolean enabled);
    public void setVideo(boolean enabled);
    public void setRemoteVideo(boolean enabled);

    public boolean isMicMuted();
    public boolean isSpeakerEnabled();
    public boolean isVideoEnabled();
    public boolean isReceivingRemoteVideo();

    public void hangup();
}
