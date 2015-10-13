package rtdc.core.view;

import rtdc.core.model.Message;
import rtdc.core.model.User;

import java.util.List;

public interface MessageListView extends View {

    void setRecentContacts(List<Message> recentContacts);

    void setMessages(List<Message> messages);

    void setContacts(List<User> contacts);

}
