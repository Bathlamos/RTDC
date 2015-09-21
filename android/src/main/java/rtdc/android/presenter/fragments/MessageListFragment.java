package rtdc.android.presenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import rtdc.android.R;
import rtdc.android.presenter.CreateUnitActivity;
import rtdc.core.controller.MessageListController;
import rtdc.core.model.Message;
import rtdc.core.view.MessageListView;

import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends AbstractFragment implements MessageListView {

    private ArrayAdapter<Message> adapter;
    private ArrayList<Message> messages = new ArrayList<Message>();
    private MessageListController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        AdapterView messageListView = (AdapterView) view.findViewById(R.id.messageListView);

        adapter = new MessageListAdapter(getActivity(), messages);
        messageListView.setAdapter(adapter);

        if (controller == null)
            controller = new MessageListController(this);

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
    public void setMessages(List<Message> messages) {
        this.messages.clear();
        this.messages.addAll(messages);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}