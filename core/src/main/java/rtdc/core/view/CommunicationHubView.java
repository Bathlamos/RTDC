package rtdc.core.view;

import rtdc.core.model.Message;
import rtdc.core.model.User;

import java.util.List;

public interface CommunicationHubView extends View {

    void setRecentContacts(List<Message> recentContacts);

    void setMessages(List<Message> messages);

    void setContacts(List<User> contacts);

    void addMessage(Message message);

    void addMessagesAtStart(List<Message> messages);

    User getMessagingUser();
}
