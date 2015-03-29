package rtdc.core;

import rtdc.core.impl.Factory;
import rtdc.core.util.Cache;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bootstrapper {

    public static Factory FACTORY;

    public static String AUTHENTICATION_TOKEN;

    public static void initialize(Factory factory){
        Bootstrapper.FACTORY = factory;

        String authenticationToken = (String) Cache.getInstance().get("authenticationToken");
        Logger.getLogger("RTDC").log(Level.INFO, "Authentication Token: " + authenticationToken);
        if(authenticationToken != null){
            Bootstrapper.AUTHENTICATION_TOKEN = authenticationToken;
            Bootstrapper.FACTORY.newDispatcher().goToAllUnits(null);
        }else
            Bootstrapper.FACTORY.newDispatcher().goToLogin(null);
    }

}
