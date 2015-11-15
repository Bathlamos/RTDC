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

        currentUser = (User) Cache.getInstance().retrieve("user");
        if (currentUser != null) {
            view.setTitle("Edit User");
            view.setUsernameAsString(currentUser.getUsername());
            view.setEmailAsString(currentUser.getEmail());
            view.setFirstnameAsString(currentUser.getFirstName());
            view.setSurnameAsString(currentUser.getLastName());
            view.setPhoneAsLong(currentUser.getPhone());
            view.setRoleAsString(currentUser.getRole());
            view.setPermissionAsString(currentUser.getPermission());
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
        newUser.setUsername(view.getUsernameAsString());
        newUser.setFirstName(view.getFirstnameAsString());
        newUser.setLastName(view.getSurnameAsString());
        newUser.setEmail(view.getEmailAsString());
        newUser.setPhone(view.getPhoneAsLong());
        newUser.setPermission(view.getPermissionAsString());
        newUser.setRole(view.getRoleAsString());
        String password = view.getPasswordAsString();

        Service.updateOrSaveUser(newUser, password);

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
