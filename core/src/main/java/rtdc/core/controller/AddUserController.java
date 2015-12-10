/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    private String currentAction;

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

        if (currentUser != null) {
            currentAction = "edit";
            currentUser.setId(currentUser.getId());
        } else {
            currentAction = "add";
            currentUser = new User();
        }
        currentUser.setUsername(view.getUsernameUiElement().getValue());
        currentUser.setFirstName(view.getFirstNameUiElement().getValue());
        currentUser.setLastName(view.getLastNameUiElement().getValue());
        currentUser.setEmail(view.getEmailUiElement().getValue());
        currentUser.setPhone(view.getPhoneUiElement().getValue());
        currentUser.setPermission(view.getPermissionUiElement().getValue());
        currentUser.setRole(view.getRoleUiElement().getValue());
        String password = view.getPasswordUiElement().getValue();

        if(currentAction.equals("edit")) {
            Service.updateUser(currentUser, password, changePassword);
        } else {
            Service.addUser(currentUser, password);
        }
    }

    public void deleteUser(){
        if (currentUser != null) {
            currentAction = "delete";
            Service.deleteUser(currentUser.getId());
        }
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

            if(!SimpleValidator.isNotEmpty(view.getConfirmPasswordUiElement().getValue()))
                validateConfirmPasswordUiElement();
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

    @Override
    public void onActionComplete(ActionCompleteEvent event) {
        if(event.getObjectType().equals("user")){
            if(currentAction.equals("add"))
                currentUser.setId(event.getObjectId());

            Cache.getInstance().put("user", new Pair(currentAction, currentUser));
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
