package rtdc.android.presenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.core.Session;
import rtdc.core.Config;
import rtdc.core.controller.MessageListController;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.event.FetchMessagesEvent;
import rtdc.core.model.Message;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.MessageListView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageListFragment extends AbstractFragment implements MessageListView {

    private ArrayAdapter<Message> recentContactsAdapter;
    private ArrayAdapter<Message> messagesAdapter;
    private ArrayList<Message> recentContacts = new ArrayList<Message>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private MessageListController controller;
    private int selectedRecentContactIndex;
    private User messagingUser;
    private View view;

    private static MessageListFragment instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instance = this;
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        final AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);
        final AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);

        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Message message = new Message();

                message.setSender(Session.getCurrentSession().getUser());
                message.setReceiver(messagingUser);
                message.setStatus(Message.Status.sent);
                message.setTimeSent(new Date());
                message.setContent(((TextView) view.findViewById(R.id.messageEditText)).getText().toString());

                // We need to wait until the server gives the message an id before we send that it over
                ActionCompleteEvent.Handler messageSentHandler = new ActionCompleteEvent.Handler() {
                    @Override
                    public void onActionComplete(ActionCompleteEvent event) {
                        if(event.getObjectType().equals("message") && event.getAction().equals("update")){
                            Event.unsubscribe(ActionCompleteEvent.TYPE, this);
                            message.setId(event.getObjectId());
                            AndroidVoipController.get().sendMessage(message);
                            addMessage(message);

                            // Force the message list view to go to the bottom
                            messageListView.setSelection(messageListView.getCount() - 1);
                        }
                    }
                };
                Event.subscribe(ActionCompleteEvent.TYPE, messageSentHandler);

                Service.saveOrUpdateMessage(message);

                ((TextView) view.findViewById(R.id.messageEditText)).setText("");
            }
        });

        recentContactsAdapter = new RecentContactsListAdapter(getActivity(), recentContacts);
        recentContactsListView.setAdapter(recentContactsAdapter);

        recentContactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Message message = recentContacts.get(position);
                selectedRecentContactIndex = position;

                // We changed the conversation. Get all the messages for this contact and display them

                User otherUser = message.getSender().getId() == Session.getCurrentSession().getUser().getId() ? message.getReceiver() : message.getSender();
                Logger.getLogger(MessageListFragment.class.getName()).log(Level.INFO, "Getting conversation with " + otherUser.getFirstName() + " " + otherUser.getLastName());
                Event.subscribe(FetchMessagesEvent.TYPE, new FetchMessagesEvent.Handler() {
                    @Override
                    public void onMessagesFetched(FetchMessagesEvent event) {
                        Event.unsubscribe(FetchMessagesEvent.TYPE, this);
                        Logger.getLogger(MessageListFragment.class.getName()).log(Level.INFO, "Conversation was fetched");
                        setMessages(event.getMessages().asList());
                    }
                });

                // Send request to the server
                Service.getMessages(message.getSender().getId(), message.getReceiver().getId());
            }
        });

        messagesAdapter = new MessageListAdapter(getActivity(), messages);
        messageListView.setAdapter(messagesAdapter);

        if (controller == null)
            controller = new MessageListController(this);

        controller.setupMessageList();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_communication_hub, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_compose_message:
                //intent = new Intent(getActivity(), CreateUnitActivity.class);
              //  startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setRecentContacts(List<Message> recentContacts) {
        this.recentContacts.clear();
        this.recentContacts.addAll(recentContacts);
        recentContactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(convertMessages(messages));
        //this.messages.addAll(messages);

        // Get the (new) user we're chatting with

        if(messages.get(0).getSender().getId() != Session.getCurrentSession().getUser().getId())
            messagingUser = messages.get(0).getSender();
        else
            messagingUser = messages.get(0).getReceiver();
        ((TextView)view.findViewById(R.id.receiverNameTextView)).setText(messagingUser.getFirstName() + " " + messagingUser.getLastName());
        ((TextView)view.findViewById(R.id.receiverRoleTextView)).setText(messagingUser.getRole());

        messagesAdapter.notifyDataSetChanged();

        AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);
        recentContactsListView.setSelection(0);
    }

    // Convert raw messages to be list adapter friendly
    private ArrayList<Message> convertMessages(List<Message> rawMessages){
        ArrayList<Message> convertedMessages = new ArrayList<Message>();
        Message lm = new Message();  // Last message
        Message message = new Message();

        for(Message m: rawMessages){
            if(m.getSenderID() == lm.getSenderID() && isSameDay(m.getTimeSent(), lm.getTimeSent())){
                Message lastMessage = convertedMessages.get(convertedMessages.size() - 1);
                lastMessage.setContent(lastMessage.getContent() + "\n\n" + m.getContent());
            } else {
                if(!isSameDay(m.getTimeSent(), lm.getTimeSent())){
                    message.setSender(m.getSender());
                    message.setContent(Config.COMMAND_EXEC_KEY);
                    message.setTimeSent(m.getTimeSent());
                    convertedMessages.add(message);
                    message = new Message();
                }
                message.setSender(m.getSender());
                message.setTimeSent(m.getTimeSent());
                message.setContent(m.getContent());
                convertedMessages.add(message);
                message = new Message();
            }

            lm = m;
        }

        return convertedMessages;
    }

    // Determine if 2 dates are in the same day
    private boolean isSameDay(Date date1, Date date2){
        if(date1 == null || date2 == null){
            return false;
        } else {
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
        }
    }

    private Message convertMessage(Message rawMessage){
        Message lastMessage = messages.get(messages.size() - 1);
        User lastSender = lastMessage.getSender();
        Date lastTimeSent = null;
        Message message = new Message();

        if(rawMessage.getSender().getId() == lastSender.getId()){
            lastMessage.setContent(lastMessage.getContent() + "\n\n" + rawMessage.getContent());
            return null;
        } else {
            message.setSender(rawMessage.getSender());
            message.setTimeSent(rawMessage.getTimeSent());
            message.setContent(rawMessage.getContent());
            return message;
        }
    }

    public void addMessage(final Message message){
        message.setStatus(Message.Status.read);
        Message convertedMessage = convertMessage(message);
        if(convertedMessage != null)
            messages.add(convertedMessage);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messagesAdapter.notifyDataSetChanged();

                // Force the message list view to go to the bottom
                AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);
                messageListView.setSelection(messageListView.getCount() - 1);

                // Get the currently selected recent contact message, remove it, and then place this message at the top of the list
                final AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);
                recentContacts.remove(selectedRecentContactIndex);
                recentContacts.add(0, message);
                selectedRecentContactIndex = 0;
                recentContactsListView.setSelection(0);
                recentContactsAdapter.notifyDataSetChanged();
            }
        });
    }

    public void addRecentContact(Message message){
        final AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);

        // Remove old contact message if there is one present
        for(Iterator<Message> iterator = recentContacts.iterator(); iterator.hasNext();){
            Message contact = iterator.next();
            if(contact.getSenderID() == message.getSenderID() || contact.getReceiverID() == message.getSenderID()){
                recentContacts.remove(contact);
            }
        }

        // Add contact to the top of the list
        recentContacts.add(0, message);
        selectedRecentContactIndex = 0;
        recentContactsListView.setSelection(0);
        recentContactsAdapter.notifyDataSetChanged();
    }

    public User getMessagingUser() {
        return messagingUser;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
        instance = null;
    }

    public static MessageListFragment getInstance(){
        return instance;
    }
}