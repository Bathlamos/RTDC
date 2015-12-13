/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.core;

import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.event.SessionExpiredEvent;
import rtdc.core.impl.Factory;
import rtdc.core.impl.Storage;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bootstrapper{

    public static String AUTHENTICATION_TOKEN;

    private static Factory FACTORY;
    private static Logger logger = Logger.getLogger(Bootstrapper.class.getName());

    private static final ErrorEvent.Handler errorHandler = new ErrorEvent.Handler(){
        @Override
        public void onError(ErrorEvent event) {
            logger.log(Level.SEVERE, event.getDescription());
            Event.unsubscribe(AuthenticationEvent.TYPE, authHandler);
            Event.unsubscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
            Event.unsubscribe(ErrorEvent.TYPE, errorHandler);
            FACTORY.newDispatcher().goToLogin(null);
        }
    };

    private static final AuthenticationEvent.Handler authHandler = new AuthenticationEvent.Handler() {

        @Override
        public void onAuthenticate(AuthenticationEvent event) {
            logger.log(Level.INFO, "AuthenticationEvent received");
            AUTHENTICATION_TOKEN = event.getAuthenticationToken();
            FACTORY.getStorage().add(Storage.KEY_AUTH_TOKEN, AUTHENTICATION_TOKEN);
            Cache.getInstance().put("sessionUser", event.getUser());
            FACTORY.getVoipController().registerUser(event.getUser(), event.getAsteriskPassword());
            FACTORY.newDispatcher().goToCapacityOverview(null);
            Event.unsubscribe(AuthenticationEvent.TYPE, authHandler);
            Event.unsubscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
            Event.unsubscribe(ErrorEvent.TYPE, errorHandler);
        }

    };

    private static final SessionExpiredEvent.Handler sessionExpiredHandler = new SessionExpiredEvent.Handler(){

        @Override
        public void onSessionExpired() {
            logger.log(Level.INFO, "SessionExpiredEvent received");
            Bootstrapper.FACTORY.newDispatcher().goToLogin(null);
            Event.unsubscribe(AuthenticationEvent.TYPE, authHandler);
            Event.unsubscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
            Event.unsubscribe(ErrorEvent.TYPE, errorHandler);
        }

    };

    public static void initialize(Factory factory){
        FACTORY = factory;

        AUTHENTICATION_TOKEN = factory.getStorage().retrieve(Storage.KEY_AUTH_TOKEN);

        logger.log(Level.INFO, "Authentication Token: " + AUTHENTICATION_TOKEN);
        if(AUTHENTICATION_TOKEN == null || AUTHENTICATION_TOKEN.isEmpty()) {
            logger.log(Level.INFO, "Now going to login");
            Bootstrapper.FACTORY.newDispatcher().goToLogin(null);
        }else {
            logger.log(Level.INFO, "Now subscribing to AuthenticationEvent and SessionExpiredEvent");
            Event.subscribe(AuthenticationEvent.TYPE, authHandler);
            Event.subscribe(SessionExpiredEvent.TYPE, sessionExpiredHandler);
            Event.subscribe(ErrorEvent.TYPE, errorHandler);
            Service.isAuthTokenValid();
        }
    }

    public static Factory getFactory(){
        if(FACTORY == null)
            throw new RuntimeException("This is accessed too early");
        return FACTORY;
    }
}
