package rtdc.android.presenter.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import rtdc.android.R;

import rtdc.android.presenter.CreateUserActivity;
import rtdc.core.controller.UserListController;
import rtdc.core.model.User;
import rtdc.core.view.UserListView;

import java.util.ArrayList;
import java.util.List;

public class ManageUsersFragment extends AbstractFragment implements AbsListView.OnItemClickListener, UserListView {

    private UserListController controller;

    private List<User> users = new ArrayList<User>();
    private UserListAdapter mAdapter;

    @Override
    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_users, container, false);
        setHasOptionsMenu(true);

        // Set the adapter
        AbsListView mListView = (AbsListView) view.findViewById(R.id.users_listView);
        mAdapter = new UserListAdapter(getActivity().getBaseContext(), users);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        if(controller == null)
            controller = new UserListController(this);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_manage_units, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(getActivity(), CreateUserActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        User user = (User) mAdapter.getItem(position);
        controller.editUser(user);
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }

}
