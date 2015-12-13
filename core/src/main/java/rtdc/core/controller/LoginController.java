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

package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.impl.Storage;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
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
        Cache.getInstance().put("sessionUser", event.getUser());
        Bootstrapper.FACTORY.getVoipController().registerUser(event.getUser(), event.getAsteriskPassword());
        Event.unsubscribe(AuthenticationEvent.TYPE, this);
        Bootstrapper.FACTORY.newDispatcher().goToManageUnits(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(AuthenticationEvent.TYPE, this);
    }

}
