package rtdc.core;

import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.Event;
import rtdc.core.event.SessionExpiredEvent;
import rtdc.core.impl.Factory;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.BootstrapperView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bootstrapper{

    public static Factory FACTORY;
    public static String AUTHENTICATION_TOKEN;
    public static BootstrapperView VIEW;

    private static boolean initialized = false;
    private static Logger logger = Logger.getLogger(Bootstrapper.class.getCanonicalName());

    private static final AuthenticationEvent.Handler authHandler = new AuthenticationEvent.Handler() {

        @Override
        public void onAuthenticate(AuthenticationEvent event) {
            logger.log(Level.INFO, "AuthenticationEvent received");
            AUTHENTICATION_TOKEN = event.getAuthenticationToken();
            VIEW.saveAuthenticationToken(AUTHENTICATION_TOKEN);
            FACTORY.newDispatcher().goToAllUnits(null);
            Event.unsubscribe(AuthenticationEvent.TYPE, authHandler);
            Event.unsubscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
        }

    };

    private static final SessionExpiredEvent.Handler sessionExpiredHandler = new SessionExpiredEvent.Handler(){

        @Override
        public void onSessionExpired() {
            logger.log(Level.INFO, "SessionExpiredEvent received");
            Bootstrapper.FACTORY.newDispatcher().goToLogin(null);
            Event.unsubscribe(AuthenticationEvent.TYPE, authHandler);
            Event.unsubscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
        }

    };


    public static void initialize(Factory factory, BootstrapperView view){
        if(initialized)
            throw new RuntimeException("Bootstraper.initialize() has already been called.");

        initialized = true;

        FACTORY = factory;
        VIEW = view;

        AUTHENTICATION_TOKEN = view.getAuthenticationToken();
        logger.log(Level.INFO, "Authentication Token: " + AUTHENTICATION_TOKEN);
        if(AUTHENTICATION_TOKEN == null) {
            logger.log(Level.INFO, "Now going to login");
            Bootstrapper.FACTORY.newDispatcher().goToLogin(null);
        }else {
            logger.log(Level.INFO, "Now subscribing to AuthenticationEvent and SessionExpiredEvent");
            Event.subscribe(AuthenticationEvent.TYPE, authHandler);
            Event.subscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
            Service.isAuthTokenValid();
        }
    }
}
