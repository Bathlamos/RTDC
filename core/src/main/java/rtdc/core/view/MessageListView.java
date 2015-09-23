package rtdc.core.view;

import rtdc.core.model.Message;

import java.util.List;

public interface MessageListView extends View {

    void setRecentContacts(List<Message> recentContacts);

    void setMessages(List<Message> messages);

}
