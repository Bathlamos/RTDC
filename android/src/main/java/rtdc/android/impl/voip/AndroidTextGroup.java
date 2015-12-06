package rtdc.android.impl.voip;

import org.linphone.core.LinphoneChatMessage;
import org.linphone.core.LinphoneChatRoom;
import rtdc.core.impl.voip.TextGroup;
import rtdc.core.impl.voip.TextMessage;

public class AndroidTextGroup implements TextGroup {

    private LinphoneChatRoom linphoneChatRoom;

    public AndroidTextGroup(LinphoneChatRoom linphoneChatRoom){
        this.linphoneChatRoom = linphoneChatRoom;
    }

    @Override
    public TextMessage createTextMessage(String message) {
        return new AndroidTextMessage(linphoneChatRoom.createLinphoneChatMessage(message));
    }

    @Override
    public void sendTextMessage(TextMessage textMessage) {
        LinphoneChatMessage linphoneChatMessage = ((AndroidTextMessage) textMessage).getLinphoneChatMessage();
        linphoneChatRoom.sendChatMessage(linphoneChatMessage);
    }
}
