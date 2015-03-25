package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.UserListView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class UserListController extends Controller<UserListView> implements FetchUsersEvent.Handler {

    private ImmutableSet<User> user = ImmutableSet.of();

    public UserListController(UserListView view){
        super(view);
        Event.subscribe(FetchUsersEvent.TYPE, this);
        Service.getUsers();
    }

    public List<User> sortUsers(User.Properties property){
        LinkedList<User> sortedUsers = new LinkedList<>(user);
        Collections.sort(sortedUsers, SimpleComparator.forProperty(property));
        return sortedUsers;
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        user = event.getUsers();
        view.setUsers(sortUsers(User.Properties.lastName));
    }
}
