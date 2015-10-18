package rtdc.android.presenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import rtdc.android.R;
import rtdc.android.impl.AndroidVoipController;
import rtdc.core.Session;
import rtdc.core.Config;
import rtdc.core.controller.MessageListController;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.event.FetchMessagesEvent;
import rtdc.core.event.MessageSavedEvent;
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
    private boolean loadingMessages;
    private View view;

    private static MessageListFragment instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        instance = this;
        view = inflater.inflate(R.layout.fragment_messages, container, false);
        final AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);
        final AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);

        // Setup message send button
        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((TextView) view.findViewById(R.id.messageEditText)).getText().equals(""))
                    return;

                final Message message = new Message();
                message.setSender(Session.getCurrentSession().getUser());
                message.setReceiver(messagingUser);
                message.setStatus(Message.Status.sent);
                message.setTimeSent(new Date());
                message.setContent(((TextView) view.findViewById(R.id.messageEditText)).getText().toString());

                // We need to wait until the server gives the message an id and a universal time before we can send it
                MessageSavedEvent.Handler messageSentHandler = new MessageSavedEvent.Handler() {
                    @Override
                    public void onMessageSaved(MessageSavedEvent event) {
                        Event.unsubscribe(MessageSavedEvent.TYPE, this);
                        message.setId(event.getMessageId());
                        message.setTimeSent(event.getTimeSent()); // Override the time with the time of the server (to keep it universal)
                        AndroidVoipController.get().sendMessage(message);
                        addMessage(message);

                        // Force the message list view to go to the bottom
                        messageListView.setSelection(messageListView.getCount() - 1);
                    }
                };
                Event.subscribe(MessageSavedEvent.TYPE, messageSentHandler);

                Service.saveOrUpdateMessage(message);

                ((TextView) view.findViewById(R.id.messageEditText)).setText("");
            }
        });

        // Setup audio call button
        view.findViewById(R.id.audioCallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.getLogger(MessageListFragment.class.getName()).log(Level.INFO, "Calling " + messagingUser.getFirstName() + " " + messagingUser.getLastName());
                AndroidVoipController.get().call(messagingUser, false);
            }
        });

        // Setup video call button
        view.findViewById(R.id.videoCallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.getLogger(MessageListFragment.class.getName()).log(Level.INFO, "Calling with video " + messagingUser.getFirstName() + " " + messagingUser.getLastName());
                AndroidVoipController.get().call(messagingUser, true);
            }
        });

        recentContactsAdapter = new RecentContactsListAdapter(getActivity(), recentContacts);
        recentContactsListView.setAdapter(recentContactsAdapter);

        // Setup item click for the recent contact's list
        recentContactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Message message = recentContacts.get(position);

                // We pressed on the conversation that's already loaded, don't do anything
                if(message.getSenderID() == messagingUser.getId() || message.getReceiverID() == messagingUser.getId())
                    return;

                selectedRecentContactIndex = position;

                // We changed the conversation. Get all the messages for this contact and display them
                Service.getMessages(message.getSender().getId(), message.getReceiver().getId(), 0, controller.FETCHING_SIZE);
            }
        });

        // The listener that takes charge of loading new messages for the conversation when we reach the top of the screen
        ((ListView)messageListView).setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Only get new messages if we're not loading messages already, we are currently in a conversation, we've reached
                // the top of the listview and we have the minimum amount of messages that are being displayed
                if(!loadingMessages && messagingUser != null && view.getChildAt(0).getTop() == 0 && controller.getMessages().size() >= 25){
                    loadingMessages = true;
                    Logger.getLogger(MessageListFragment.class.getName()).log(Level.INFO, "Fetching more messages for the conversation...");
                    Service.getMessages(messagingUser.getId(), Session.getCurrentSession().getUser().getId(), controller.getMessages().size()-1, controller.FETCHING_SIZE);
                }
            }
        });

        messagesAdapter = new MessageListAdapter(getActivity(), messages);
        messageListView.setAdapter(messagesAdapter);

        if (controller == null)
            controller = new MessageListController(this);

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

        // If we're the receiver and the status of some messages isn't read, we need to notify the server that we now have read them
        for(Message message: messages){
            if(message.getReceiverID() == Session.getCurrentSession().getUser().getId() && message.getStatus() != Message.Status.read) {
                message.setStatus(Message.Status.read);
                Service.saveOrUpdateMessage(message);
                recentContacts.get(selectedRecentContactIndex).setStatus(Message.Status.read);
                recentContactsAdapter.notifyDataSetChanged();
            }
        }

        // Force the message list view to go to the bottom
        AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);
        messageListView.setSelection(messages.size());

        messagesAdapter.notifyDataSetChanged();

        AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);
        recentContactsListView.setSelection(0);

        // Make sure that this parameter is reset to its default value
        loadingMessages = false;
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

    public void addMessagesAtStart(final List<Message> messages){
        final List<Message> convertedMessages = convertMessages(messages);

        Message lastConvertedMessage = convertedMessages.get(convertedMessages.size() - 1);
        Message topMessageInList = this.messages.get(0);
        if (isSameDay(lastConvertedMessage.getTimeSent(), topMessageInList.getTimeSent())) {

            // Add the last converted message at the top of the first message of the currently displayed list
            this.messages.get(1).setContent(lastConvertedMessage.getContent() + "\n\n" + this.messages.get(1).getContent());

            convertedMessages.remove(lastConvertedMessage);         // Remove the last converted message
            convertedMessages.remove(convertedMessages.size() - 1); // Don't forget to remove the date header
        }

        this.messages.addAll(0, convertedMessages);

        // Update the listview and keep the selection where we we're before we loaded the new messages
        AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);
        messagesAdapter.notifyDataSetChanged();
        messageListView.setSelection(convertedMessages.size());

        // Only say we loaded the messages after the listview as properly updated
        messageListView.post(new Runnable() {
            @Override
            public void run() {
                loadingMessages = false;
            }
        });
    }

    public void addRecentContact(Message message){
        final AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);

        // Remove old contact message if there is one present
        for(Iterator<Message> iterator = recentContacts.iterator(); iterator.hasNext();){
            Message contact = iterator.next();
            if(contact.getSenderID() == message.getSenderID() || contact.getReceiverID() == message.getSenderID()){
                iterator.remove();
            }
        }

        // Add contact to the top of the list
        recentContacts.add(0, message);
        getActivity().runOnUiThread(new Runnable(){
            @Override
            public void run() {
                selectedRecentContactIndex = 0;
                recentContactsListView.setSelection(0);
                recentContactsAdapter.notifyDataSetChanged();
            }
        });
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