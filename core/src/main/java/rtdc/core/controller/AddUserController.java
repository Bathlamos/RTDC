package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.exception.ValidationException;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.view.AddUserView;

import java.util.logging.Logger;

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

    public void addUser(boolean changePassword) {

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

        if(currentUser != null) {
            Service.updateUser(newUser, password, changePassword);
        } else {
            Service.addUser(newUser, password);
        }

        Cache.getInstance().put("user", new Pair(action, newUser));
    }

    public void deleteUser(){
        Cache.getInstance().put("user", new Pair("delete", currentUser));
        if (currentUser != null)
            Service.deleteUser(currentUser.getId());
    }

    public void validateUsernameUiElement(){
        try {
            SimpleValidator.validateUsername(view.getUsernameUiElement().getValue());
            view.getUsernameUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getUsernameUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validatePasswordUiElement(){
        try {
            SimpleValidator.validatePassword(view.getPasswordUiElement().getValue());
            view.getPasswordUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getPasswordUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateConfirmPasswordUiElement(){
        if(!view.getConfirmPasswordUiElement().getValue().equals(view.getPasswordUiElement().getValue()))
            view.getConfirmPasswordUiElement().setErrorMessage("Confirmation doesn't match the password given");
    }

    public void validateEmailUiElement(){
        try {
            SimpleValidator.validateEmail(view.getEmailUiElement().getValue());
            view.getEmailUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getEmailUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateFirstNameUiElement(){
        try {
            SimpleValidator.validatePersonFirstName(view.getFirstNameUiElement().getValue());
            view.getFirstNameUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getFirstNameUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateLastNameUiElement(){
        try {
            SimpleValidator.validatePersonLastName(view.getLastNameUiElement().getValue());
            view.getLastNameUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getLastNameUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validatePhoneNumberUiElement(){
        try {
            SimpleValidator.isNumber(view.getPhoneUiElement().getValue());
            view.getPhoneUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getPhoneUiElement().setErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onActionComplete(ActionCompleteEvent event) {
        if(event.getObjectType().equals("user")){
            view.closeDialog();
        }
    }

    // Determine if we are creating a new user or editing an existing one
    public boolean isNewUser(){
        return currentUser == null;
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(ActionCompleteEvent.TYPE, this);
    }
}
