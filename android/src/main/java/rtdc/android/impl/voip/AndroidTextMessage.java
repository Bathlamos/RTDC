package rtdc.android.impl.voip;

import org.linphone.core.LinphoneChatMessage;
import rtdc.core.impl.voip.Address;
import rtdc.core.impl.voip.TextMessage;

public class AndroidTextMessage implements TextMessage{

    private LinphoneChatMessage linphoneChatMessage;

    public AndroidTextMessage(LinphoneChatMessage linphoneChatMessage){
        this.linphoneChatMessage = linphoneChatMessage;
    }

    public LinphoneChatMessage getLinphoneChatMessage(){
        return linphoneChatMessage;
    }

    @Override
    public String getText() {
        return linphoneChatMessage.getText();
    }

    @Override
    public Address getFrom() {
        return new AndroidAddress(linphoneChatMessage.getFrom());
    }

    @Override
    public Address getTo() {
        return new AndroidAddress(linphoneChatMessage.getTo());
    }
}
