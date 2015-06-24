package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.impl.Storage;
import rtdc.core.service.Service;
import rtdc.core.view.LoginView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController extends Controller<LoginView> implements AuthenticationEvent.Handler, ErrorEvent.Handler {

    private static final Logger logger = Logger.getLogger(LoginController.class.getCanonicalName());

    public LoginController(LoginView view){
        super(view);

        view.getUsernameUiElement().setFocus(true);
    }

    @Override
    String getTitle() {
        return "Login";
    }

    public void login(){
        logger.log(Level.INFO, "Subscribing to AuthenticationEvent");
        Event.subscribe(AuthenticationEvent.TYPE, this);

        String username = view.getUsernameUiElement().getValue();
        String password = view.getPasswordUiElement().getValue();

        if(username.isEmpty()) {
            view.getUsernameUiElement().setErrorMessage("Username cannot be empty");
            view.getUsernameUiElement().setFocus(true);
        } else if(password.isEmpty()) {
            view.getPasswordUiElement().setErrorMessage("Password cannot be empty");
            view.getPasswordUiElement().setFocus(true);
        } else
            Service.authenticateUser(username, password);
    }

    @Override
    public void onAuthenticate(AuthenticationEvent event) {
        logger.log(Level.INFO, "AuthenticationEvent received");
        Bootstrapper.AUTHENTICATION_TOKEN = event.getAuthenticationToken();
        Bootstrapper.FACTORY.getStorage().add(Storage.KEY_AUTH_TOKEN, Bootstrapper.AUTHENTICATION_TOKEN);
        Bootstrapper.FACTORY.newDispatcher().goToAllUnits(this);
        Event.unsubscribe(AuthenticationEvent.TYPE, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(AuthenticationEvent.TYPE, this);
    }

}
