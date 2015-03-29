package rtdc.core.controller;

import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.view.AddUserView;

public class AddUserController extends Controller<AddUserView> implements ActionCompleteEvent.Handler {


    public AddUserController(AddUserView view){
        super(view);
        Event.subscribe(ActionCompleteEvent.TYPE, this);
    }

    @Override
    String getTitle() {
        return "Add User";
    }

    public void addUser() {

        User newUser = new User();
        newUser.setUsername(view.getUsernameAsString());
        newUser.setFirstName(view.getFirstnameAsString());
        newUser.setLastName(view.getSurnameAsString());
        newUser.setEmail(view.getEmailAsString());
        newUser.setPermission(view.getPermissionAsString());
        newUser.setRole(view.getRoleAsString());
        String password = view.getPasswordAsString();

        Service.updateOrSaveUser(newUser, password);
    }

    public void deleteUser(User user){
        Service.deleteUser(user.getId());
    }

    @Override
    public void onActionComplete(ActionCompleteEvent event) {
        view.displayError("Success", "success");
    }
}
