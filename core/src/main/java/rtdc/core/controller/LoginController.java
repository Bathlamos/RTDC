package rtdc.core.controller;

import rtdc.core.model.AuthenticationInformation;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.LoginView;

public class LoginController {

    private LoginView view;

    public LoginController(LoginView view){
        this.view = view;
    }

    public void login(){
        AuthenticationInformation authInfo = new AuthenticationInformation();
        authInfo.setUsername(view.getUsername());
        authInfo.setPassword(view.getPassword());

        Service.authenticateUser(authInfo, new AsyncCallback<User>() {
            @Override
            public void onCallback(User user) {
                view.setUsername("Yay! You logged in :)   ->   " + user.getFirstName());
            }
        });
    }



}
