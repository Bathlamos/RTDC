package rtdc.android.presenter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import com.google.common.base.Joiner;
import rtdc.android.R;
import rtdc.core.controller.UserListController;
import rtdc.core.model.User;
import rtdc.core.view.UserListView;

import java.util.ArrayList;
import java.util.List;

public class CommunicationHubContactListFragment extends AbstractFragment implements UserListView {

    private View view;
    private ArrayList<User> contacts = new ArrayList<User>();
    private ContactListAdapter adapter;
    private UserListController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_communication_hub_contact_list, container, false);
        setHasOptionsMenu(true);

        AdapterView actionListView = (AdapterView) view.findViewById(R.id.contactList);
        adapter = new ContactListAdapter(getActivity(), contacts);
        actionListView.setAdapter(adapter);

        if(controller == null)
            controller = new UserListController(this);

        SearchView searchBar = (SearchView) view.findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query){
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText){
                return true;
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_communication_hub, menu);
    }

    @Override
    public void setUsers(List<User> users) {
        contacts.clear();
        contacts.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private static class ContactListAdapter extends ArrayAdapter<User> {
        private Activity activity;

        public ContactListAdapter(Activity activity, List<User> actions){
            super(activity, R.layout.adapter_communication_hub_contacts, actions);
            this.activity = activity;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = activity.getLayoutInflater().inflate(R.layout.adapter_communication_hub_contacts, parent, false);

            User currentContact = getItem(position);

            TextView initials = (TextView) view.findViewById(R.id.initials);
            initials.setText(Joiner.on("").join(currentContact.getFirstName().charAt(0), currentContact.getLastName().charAt(0)));

            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(currentContact.getFirstName()+" "+currentContact.getLastName());

            TextView lastReceivedMessage = (TextView) view.findViewById(R.id.lastReceivedMessage);
            lastReceivedMessage.setText("Last message");

            TextView lastTime = (TextView) view.findViewById(R.id.lastTime);
            lastReceivedMessage.setText("Last time");
            return view;
        }
    }
}
