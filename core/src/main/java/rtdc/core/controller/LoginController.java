package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.Event;
import rtdc.core.service.Service;
import rtdc.core.view.LoginView;

public class LoginController extends Controller<LoginView> implements AuthenticationEvent.Handler {


    public LoginController(LoginView view){
        super(view);
    }

    @Override
    String getTitle() {
        return "Login";
    }

    public void login(){
        Service.authenticateUser(view.getUsernameUiElement().getValue(), view.getPasswordUiElement().getValue());
        Event.subscribe(AuthenticationEvent.TYPE, this);
    }


    @Override
    public void onAuthenticate(AuthenticationEvent event) {
        Bootstrapper.AUTHENTICATION_TOKEN = event.getAuthenticationToken();
        Bootstrapper.FACTORY.newDispatcher().goToAllUnits(this);
    }
}
