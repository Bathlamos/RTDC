package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.BootstrapperView;
import rtdc.core.view.LoginView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BootstrapperController {

    private BootstrapperView view;

    public BootstrapperController(BootstrapperView view){
        this.view = view;
    }

    public void init(){
        Logger.getLogger("RTDC").log(Level.INFO, "Authentication Token: " + view.getAuthenticationToken());
        if(view.hasAuthenticationToken()){
            Bootstrapper.AUTHENTICATION_TOKEN = view.getAuthenticationToken();
            view.goToMain();
        }else
            view.goToLogin();
    }

}
