package rtdc.core.controller;

import rtdc.core.event.Event;
import rtdc.core.event.FetchMessagesEvent;
import rtdc.core.model.Message;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.MessageListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MessageListController extends Controller<MessageListView> implements FetchMessagesEvent.Handler {

    private ArrayList<Message> messages;

    public MessageListController(MessageListView view){
        super(view);
        Event.subscribe(FetchMessagesEvent.TYPE, this);
        // TODO Implement Service.getMessage()
        // Service.getMessages();
        messages = getMessages();
    }

    @Override
    String getTitle() {
        return "Messages";
    }

    public void sortMessages(Message.Properties property){
        Collections.sort(messages, SimpleComparator.forProperty(property).build());
        view.setMessages(messages);
    }

    @Override
    public void onMessagesFetched(FetchMessagesEvent event) {
        messages = new ArrayList<>(event.getMessages());
        sortMessages(Message.Properties.timeSent);
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchMessagesEvent.TYPE, this);
    }

    // TODO SCRAP THAT
    private ArrayList<Message> getMessages(){
        ArrayList<Message> messageList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Message message = new Message();
            User sender = new User();
            sender.setFirstName("John");
            sender.setLastName("Doe");
            message.setSender(sender);
            message.setContent("Hi, I hope you're doing good! Do you think we could meet up sometime this afternoon?");
            message.setTimeSent(new Date());
            messageList.add(message);

            Message message2 = new Message();
            User sender2 = new User();
            sender2.setFirstName("Me");
            sender2.setLastName("");
            message2.setSender(sender2);
            message2.setContent("Oh hi John! Sure, lets meet around 2 PM in my office.");
            message2.setTimeSent(new Date());
            messageList.add(message2);
        }
        return messageList;
    }

}
