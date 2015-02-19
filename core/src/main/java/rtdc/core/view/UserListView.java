package rtdc.core.view;

import rtdc.core.model.User;

import java.util.List;

public interface UserListView extends View {

    void setUsers(List<User> users);

}
