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
import rtdc.core.model.Message;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.MessageListView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessageListFragment extends AbstractFragment implements MessageListView {

    private ArrayAdapter<Message> recentContactsAdapter;
    private ArrayAdapter<Message> messagesAdapter;
    private ArrayList<Message> recentContacts = new ArrayList<Message>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private MessageListController controller;
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
                Message message = new Message();

                message.setSender(Session.getCurrentSession().getUser());
                message.setReceiver(messagingUser);
                message.setStatus(Message.Status.sent);
                message.setTimeSent(new Date());
                message.setContent(((TextView) view.findViewById(R.id.messageEditText)).getText().toString());

                AndroidVoipController.get().sendMessage(message);
                addMessage(message);

                // Force the message list view to go to the bottom
                messageListView.setSelection(messageListView.getCount() - 1);

                //Service.saveMessage(message);

                ((TextView) view.findViewById(R.id.messageEditText)).setText("");
            }
        });

        recentContactsAdapter = new RecentContactsListAdapter(getActivity(), recentContacts);
        recentContactsListView.setAdapter(recentContactsAdapter);

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

        messagesAdapter.notifyDataSetChanged();
    }

    // Convert raw messages to be list adapter friendly
    private ArrayList<Message> convertMessages(List<Message> rawMessages){
        ArrayList<Message> convertedMessages = new ArrayList<Message>();
        Message lm = new Message();  // Last message
        Message message = new Message();

        for(Message m: rawMessages){
            if(m.getSender() == lm.getSender() && isSameDay(m.getTimeSent(), lm.getTimeSent())){
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

        if(rawMessage.getSender() == lastSender){
            lastMessage.setContent(lastMessage.getContent() + "\n\n" + rawMessage.getContent());
            return null;
        } else {
            message.setSender(rawMessage.getSender());
            message.setTimeSent(rawMessage.getTimeSent());
            message.setContent(rawMessage.getContent());
            return message;
        }
    }

    public void addMessage(Message message){
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
            }
        });
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