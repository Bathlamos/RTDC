package rtdc.core.impl.voip;

public interface VoIPListener {

    void callState(VoIPManager voIPManager, Call call, Call.State state, String message);

    void messageReceived(VoIPManager voIPManager, TextGroup textGroup, TextMessage textMessage);

}
