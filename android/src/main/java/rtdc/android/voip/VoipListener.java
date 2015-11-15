package rtdc.android.voip;

import org.linphone.core.LinphoneCall;
import org.linphone.core.LinphoneChatMessage;

public interface VoipListener {
    public void onMessageReceived(LinphoneChatMessage chatMessage);
    public void onCallStateChanged(LinphoneCall call, LinphoneCall.State state);
}
