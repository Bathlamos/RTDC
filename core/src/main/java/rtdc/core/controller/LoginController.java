package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.Event;
import rtdc.core.service.Service;
import rtdc.core.view.LoginView;

public class LoginController extends Controller<LoginView> implements AuthenticationEvent.AuthenticationHandler{


    public LoginController(LoginView view){
        super(view);
    }

    public void login(){
        Service.authenticateUser(view.getUsername(), view.getPassword());
        Event.subscribe(AuthenticationEvent.TYPE, this);
    }


    @Override
    public void onAuthenticate(AuthenticationEvent event) {
        view.saveAuthenticationToken(event.getAuthenticationToken());
        Bootstrapper.AUTHENTICATION_TOKEN = event.getAuthenticationToken();
        Bootstrapper.FACTORY.newDispatcher().goToAllUnits(true);
    }
}
