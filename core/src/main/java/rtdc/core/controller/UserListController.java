package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
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

    public void editUser(User user){
        Cache.getInstance().put("user", user);
        Bootstrapper.FACTORY.newDispatcher().goToEditUser(this);
    }

    @Override
    public void onUsersFetched(FetchUsersEvent event) {
        users = new HashSet<>(event.getUsers());
        view.setUsers(sortUsers(User.Properties.lastName));
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchUsersEvent.TYPE, this);
    }
}
