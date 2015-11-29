package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.view.AddUserView;

public class AddUserController extends Controller<AddUserView> implements ActionCompleteEvent.Handler {

    private User currentUser;

    public AddUserController(AddUserView view){
        super(view);
        Event.subscribe(ActionCompleteEvent.TYPE, this);

        view.getRoleUiElement().setArray(User.Role.values());
        view.getRoleUiElement().setStringifier(User.Role.getStringifier());

        view.getPermissionUiElement().setArray(User.Permission.values());
        view.getPermissionUiElement().setStringifier(User.Permission.getStringifier());

        currentUser = (User) Cache.getInstance().retrieve("user");
        if (currentUser != null) {
            view.setTitle("Edit User");
            view.getUsernameUiElement().setValue(currentUser.getUsername());
            view.getEmailUiElement().setValue(currentUser.getEmail());
            view.getFirstNameUiElement().setValue(currentUser.getFirstName());
            view.getLastNameUiElement().setValue(currentUser.getLastName());
            view.getPhoneUiElement().setValue(currentUser.getPhone());
            view.getRoleUiElement().setValue(currentUser.getRole());
            view.getPermissionUiElement().setValue(currentUser.getPermission());
        } else {
            view.hideDeleteButton();
        }
    }

    @Override
    String getTitle() {
        return "Add User";
    }

    public void addUser() {

        User newUser = new User();
        String action = "add";
        if (currentUser != null) {
            newUser.setId(currentUser.getId());
            action = "edit";
        }
        newUser.setUsername(view.getUsernameUiElement().getValue());
        newUser.setFirstName(view.getFirstNameUiElement().getValue());
        newUser.setLastName(view.getLastNameUiElement().getValue());
        newUser.setEmail(view.getEmailUiElement().getValue());
        newUser.setPhone(view.getPhoneUiElement().getValue());
        newUser.setPermission(view.getPermissionUiElement().getValue());
        newUser.setRole(view.getRoleUiElement().getValue());
        String password = view.getPasswordUiElement().getValue();

        if (currentUser != null)
            Service.updateUser(newUser, password);
        else
            Service.addUser(newUser, password);

        Cache.getInstance().put("user", new Pair(action, newUser));
        view.closeDialog();
    }

    public void deleteUser(){
        Cache.getInstance().put("user", new Pair("delete", currentUser));
        if (currentUser != null)
            Service.deleteUser(currentUser.getId());
        view.closeDialog();
    }

    @Override
    public void onActionComplete(ActionCompleteEvent event) {
        // This causes a crash
        //view.displayError("Success", "success");
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(ActionCompleteEvent.TYPE, this);
    }
}
