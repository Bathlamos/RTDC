package rtdc.core.view;

import rtdc.core.model.Message;

import java.util.List;

public interface CommunicationHubMessagingView extends View {

    void setMessages(List<Message> messages);

}
