package rtdc.core.controller;

import rtdc.core.Bootstrapper;
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
        Service.authenticateUser(view.getUsername(), view.getPassword(), new AsyncCallback<User>() {
            @Override
            public void onSuccess(User user) {
                view.saveAuthenticationToken(user.getAuthenticationToken());
                Bootstrapper.FACTORY.newDispatcher().goToAllUnits(true);
            }

            @Override
            public void onError(String message) {
                view.displayError("Error", message);
            }
        });
    }



}
