package rtdc.android.presenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import rtdc.android.R;
import rtdc.core.controller.MessageListController;
import rtdc.core.model.Message;
import rtdc.core.model.User;
import rtdc.core.view.MessageListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageListFragment extends AbstractFragment implements MessageListView {

    private ArrayAdapter<Message> recentContactsAdapter;
    private ArrayAdapter<Message> messagesAdapter;
    private ArrayList<Message> recentContacts = new ArrayList<Message>();
    private ArrayList<Message> messages = new ArrayList<Message>();
    private MessageListController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        AdapterView recentContactsListView = (AdapterView) view.findViewById(R.id.recentContactsListView);
        AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);

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
        messagesAdapter.notifyDataSetChanged();
    }

    // Implement this method!!! ***************
    private ArrayList<Message> convertMessages(List<Message> rawMessages){
        ArrayList<Message> convertedMessages = new ArrayList<Message>();
        User lastSender = null;
        User sender = rawMessages.get(0).getSender();
        Date lastTimeSent = null;
        Date timeSent =  rawMessages.get(0).getTimeSent();
        String content = rawMessages.get(0).getContent();
        Message message = new Message();

        for(Message m : rawMessages){
            if(m.getSender() == lastSender){
                content += "\n\n"+m.getContent();
            } else {
                message.setSender(sender);
                message.setTimeSent(timeSent);
                message.setContent(content);
                convertedMessages.add(message);
                message = new Message();
                sender = m.getSender();
                timeSent = m.getTimeSent();
                content = m.getContent();
            }

            lastSender = m.getSender();
            lastTimeSent = m.getTimeSent();
        }

        message.setSender(sender);
        message.setTimeSent(timeSent);
        message.setContent(content);
        convertedMessages.add(message);

        return convertedMessages;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}