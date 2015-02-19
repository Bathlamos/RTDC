package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.impl.Factory;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.AddUserView;
import rtdc.core.view.LoginView;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class AddUserController {

    private AddUserView view;

    public AddUserController(AddUserView view){
        this.view = view;
    }

    public void addUser() {

        User newUser = new User();
        newUser.setUsername(view.getUsernameAsString());
        newUser.setFirstName(view.getFirstnameAsString());
        newUser.setSurname(view.getSurnameAsString());
        newUser.setEmail(view.getEmailAsString());
        newUser.setPermission(view.getPermissionAsString());
        newUser.setRole(view.getRoleAsString());
        String password = view.getPasswordAsString();

        Set<ConstraintViolation<User>> constraintViolations = Bootstrapper.FACTORY.newValidator().validate(newUser);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<User> first = constraintViolations.iterator().next();
            view.displayError("Error", first.getPropertyPath() + " : " + first.getMessage());
        } else if (password == null || password.isEmpty() || password.length() < 4)
            view.displayError("Error", "Password needs to be at least 4 characters");
        else {
            Service.updateOrSaveUser(newUser, password, new AsyncCallback<Boolean>() {

                @Override
                public void onSuccess(Boolean result) {
                    view.displayError("Success", "Success");
                }

                @Override
                public void onError(String message) {
                    view.displayError("CommError", message);
                }
            });
        }
    }
}
