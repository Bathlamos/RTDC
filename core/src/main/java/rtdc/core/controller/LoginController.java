package rtdc.core.controller;

import rtdc.core.view.LoginView;

public class LoginController {

    private LoginView view;

    public LoginController(LoginView view){
        this.view = view;
    }

    public void login(){
        String username = view.getUsername();
        String password = view.getPassword();


    }



}
