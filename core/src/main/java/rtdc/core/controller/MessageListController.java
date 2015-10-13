package rtdc.core.controller;

import rtdc.core.Session;
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
        Service.getRecentContacts(Session.getCurrentSession().getUser().getId());
    }

    @Override
    String getTitle() {
        return "Messages";
    }

    public void sortRecentContacts(Message.Properties property){
        Collections.sort(recentContacts, SimpleComparator.forProperty(property).build());
        view.setRecentContacts(recentContacts);

        // We load the messages for the most recent contact
        Service.getMessages(recentContacts.get(0).getSender().getId(), recentContacts.get(0).getReceiver().getId());
    }

    @Override
    public void onMessagesFetched(FetchMessagesEvent event) {
        messages = new ArrayList<>(event.getMessages());
        view.setMessages(messages);
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
}
