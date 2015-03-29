package rtdc.core.controller;

import rtdc.core.event.Event;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.UserListView;

import java.util.*;

public class UserListController extends Controller<UserListView> implements FetchUsersEvent.Handler {

    private Set<User> users;

    public UserListController(UserListView view){
        super(view);
        Event.subscribe(FetchUsersEvent.TYPE, this);
        Service.getUsers();
    }

    @Override
    String getTitle() {
        return "Users";
    }

    public List<User> sortUsers(User.Properties property){
        LinkedList<User> sortedUsers = new LinkedList<>(users);
        Collections.sort(sortedUsers, SimpleComparator.forProperty(property));
        return sortedUsers;
    }

    public void deleteUser(User user){
        users.remove(user);
        Service.deleteUser(user.getId());
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        users = new HashSet<>(event.getUsers());
        view.setUsers(sortUsers(User.Properties.lastName));
    }
}
