package rtdc.core.impl;

import rtdc.core.model.User;

public interface VoipController {

    public void registerUser(User user);
    public void unregisterCurrentUser();
    public void call(User user);
    public void setMicMuted(boolean mute);
    public void setSpeaker(boolean speaker);
    public void acceptCall();
    public void declineCall();
    public void hangup();
}
