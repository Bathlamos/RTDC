package rtdc.android.presenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.controller.UserListController;
import rtdc.core.model.User;
import rtdc.core.view.UserListView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationHubContactFragment extends AbstractFragment implements UserListView{

    private UserListController controller;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> users = new ArrayList<User>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_communication_hub_contact, container, false);
        AdapterView contactListAdapter = (AdapterView) view.findViewById(R.id.contactListView);

        adapter = new ContactListAdapter(users, getActivity());
        contactListAdapter.setAdapter(adapter);

        if (controller == null)
            controller = new UserListController(this);

        return view;
    }

    @Override
    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        adapter.notifyDataSetChanged();
    }

    private class ContactListAdapter extends ArrayAdapter<User> {

        private LayoutInflater inflater;

        public ContactListAdapter(List<User> users, Context context) {
            super(context, R.layout.adapter_contact, users);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            if(view == null)
                view = getActivity().getLayoutInflater().inflate(R.layout.adapter_contact, parent, false);

            User currentUser = users.get(position);

            setupColumn(view, R.id.userNameText, currentUser.getFirstName() + " " + currentUser.getLastName());
            setupColumn(view, R.id.roleUnitText, currentUser.getRole());

            ImageButton audioCallButton = (ImageButton) view.findViewById(R.id.audioCallButton);
            audioCallButton.setTag(position);
            audioCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User clickedUser = users.get(Integer.parseInt(v.getTag().toString()));
                    Logger.getLogger(CommunicationHubContactListFragment.class.getName()).log(Level.INFO, "Calling " + clickedUser.getId());
                    Bootstrapper.FACTORY.getVoipController().call(clickedUser);
                }
            });

            ImageButton videoCallButton = (ImageButton) view.findViewById(R.id.audioCallButton);
            videoCallButton.setTag(position);
            videoCallButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    User clickedUser = users.get(Integer.parseInt(v.getTag().toString()));
                    Logger.getLogger(CommunicationHubContactListFragment.class.getName()).log(Level.INFO, "Calling " + clickedUser.getId());
                    // TODO Start video call
//                    Bootstrapper.FACTORY.getVoipController().call(clickedUser);
                }
            });

            return view;
        }

        private void setupColumn(View view, int resourceId, String text){
            TextView textView = (TextView) view.findViewById(resourceId);
            textView.setText(text);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
