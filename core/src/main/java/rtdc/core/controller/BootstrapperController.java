package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.BootstrapperView;
import rtdc.core.view.LoginView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BootstrapperController extends Controller<BootstrapperView> {

    public BootstrapperController(BootstrapperView view){
        super(view);
    }

    @Override
    String getTitle() {
        return "Bootstrap";
    }

    public void init(){
        Logger.getLogger("RTDC").log(Level.INFO, "Authentication Token: " + view.getAuthenticationToken());
        if(view.hasAuthenticationToken()){
            Bootstrapper.AUTHENTICATION_TOKEN = view.getAuthenticationToken();
            Bootstrapper.FACTORY.newDispatcher().goToAllUnits();
        }else
            Bootstrapper.FACTORY.newDispatcher().goToLogin();
    }

}
