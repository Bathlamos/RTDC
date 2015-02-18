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

    public void addUser(){

        User newUser = new User();
        newUser.setUsername(view.getUsernameAsString());
        newUser.setFirstName(view.getFirstnameAsString());
        newUser.setSurname(view.getSurnameAsString());


        Set<ConstraintViolation<User>> constraintViolations = Bootstrapper.FACTORY.newValidator().validate(newUser);

        for(ConstraintViolation<User> violation: constraintViolations)
            view.displayError(violation.getLeafBean().toString(), violation.getMessage() + ": " + violation.getPropertyPath());

//        Service.authenticateUser(view.getUsername(), view.getPassword(), new AsyncCallback<User>() {
//            @Override
//            public void onSuccess(User user) {
//                view.setUsername("Yay! You logged in :)   ->   " + user.getFirstName());
//            }
//
//            @Override
//            public void onError(String message) {
//                view.displayError("Error", message);
//            }
//        });
    }



}
