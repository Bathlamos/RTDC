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
import rtdc.core.view.MessageListView;

import java.util.ArrayList;
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
        this.messages.addAll(messages);
        messagesAdapter.notifyDataSetChanged();
    }


    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}