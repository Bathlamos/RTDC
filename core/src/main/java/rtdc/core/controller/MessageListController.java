package rtdc.core.controller;

import rtdc.core.event.Event;
import rtdc.core.event.FetchRecentContactsEvent;
import rtdc.core.event.FetchMessagesEvent;
import rtdc.core.model.Message;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.MessageListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MessageListController extends Controller<MessageListView> implements FetchRecentContactsEvent.Handler, FetchMessagesEvent.Handler {

    private ArrayList<Message> recentContacts;
    private ArrayList<Message> messages;

    public MessageListController(MessageListView view){
        super(view);
        Event.subscribe(FetchRecentContactsEvent.TYPE, this);
        Event.subscribe(FetchMessagesEvent.TYPE, this);
        // TODO Implement Service.getRecentContacts()
        // Service.getRecentContacts();
        recentContacts = getRecentContacts();
        sortRecentContacts(Message.Properties.timeSent);
    }

    @Override
    String getTitle() {
        return "Messages";
    }

    public void sortMessages(Message.Properties property){
        Collections.sort(messages, SimpleComparator.forProperty(property).build());
        Collections.reverse(messages);
        view.setMessages(messages);
    }

    public void sortRecentContacts(Message.Properties property){
        Collections.sort(recentContacts, SimpleComparator.forProperty(property).build());
        view.setRecentContacts(recentContacts);
    }

    @Override
    public void onMessagesFetched(FetchMessagesEvent event) {
        messages = new ArrayList<>(event.getMessages());
        sortMessages(Message.Properties.timeSent);
    }

    @Override
    public void onRecentContactsFetched(FetchRecentContactsEvent event) {
        recentContacts = new ArrayList<>(event.getRecentContacts());
        sortRecentContacts(Message.Properties.timeSent);
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchRecentContactsEvent.TYPE, this);
        Event.unsubscribe(FetchMessagesEvent.TYPE, this);
    }

    // TODO SCRAP THAT
    private ArrayList<Message> getRecentContacts(){
        ArrayList<Message> recentContacts = new ArrayList<>();

        Message message = new Message();
        User receiver = new User();
        receiver.setFirstName("John");
        receiver.setLastName("Doe");
        message.setReceiver(receiver);
        message.setContent("Hi, I hope you're doing good! Do you think we could meet up sometime this afternoon?");
        message.setTimeSent(new Date());
        message.setStatus(Message.Status.delivered);
        recentContacts.add(message);

        Message message2 = new Message();
        User receiver2 = new User();
        receiver2.setFirstName("Emilie");
        receiver2.setLastName("Luskberg");
        message2.setReceiver(receiver2);
        message2.setContent("Sounds good!");
        message2.setTimeSent(new Date());
        message2.setStatus(Message.Status.read);
        recentContacts.add(message2);

        return recentContacts;
    }

    // TODO SCRAP THAT
    public void setupMessageList(){
        messages = getMessages();
        sortMessages(Message.Properties.timeSent);
    }

    private ArrayList<Message> getMessages(){
        ArrayList<Message> messageList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            Message message = new Message();
            User sender = new User();
            sender.setFirstName("John");
            sender.setLastName("Doe");
            message.setSender(sender);
            message.setContent("Hi, I hope you're doing good! Do you think we could meet up sometime this afternoon?");
            message.setTimeSent(new Date(2015, 9, i+1));
            messageList.add(message);

            if(i == 3){
                Message message3 = new Message();
                message3.setSender(sender);
                message3.setContent("I'd like to discuss a few things about my new schedule");
                message3.setTimeSent(new Date(2015, 9, i+1));
                messageList.add(message3);

                Message message4 = new Message();
                message4.setSender(sender);
                message4.setContent("Oh I forgot to mention... I'll only be available after 1 PM");
                message4.setTimeSent(new Date(2015, 9, i+1));
                messageList.add(message4);
            }

            Message message2 = new Message();
            User sender2 = new User();
            sender2.setFirstName("Me");
            sender2.setLastName("");
            message2.setSender(sender2);
            message2.setContent("Oh hi John! Sure, lets meet around 2 PM in my office.");
            message2.setTimeSent(new Date(2015, 9, i+1));
            messageList.add(message2);
        }
        return messageList;
    }
}
