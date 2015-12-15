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

package rtdc.core.service;

import rtdc.core.config.Conf;
import rtdc.core.Bootstrapper;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.i18n.ResBundle;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Message;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.util.Cache;

import java.util.logging.Logger;

import static rtdc.core.impl.HttpRequest.RequestMethod.*;

/**
 * Single class used to make all network requests targeting our own API.
 */
public final class Service {

    private static final String URL = Conf.get().apiProtocol() + "://" +
            Conf.get().apiHost() + ":" +
            Conf.get().apiPort() +
            Conf.get().apiPath();
    private static final Logger logger = Logger.getLogger(Service.class.getCanonicalName());

    private Service(){}

    public static void authenticateUser(String username, String password){
        HttpRequest req = Bootstrapper.getFactory().newHttpRequest(URL + "auth/login", POST);
        req.addParameter("username", username);
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void isAuthTokenValid(){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "auth/tokenValid", POST));
    }

    public static void logout(){
        Cache.getInstance().remove("sessionUser");
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "auth/logout", POST));
        Bootstrapper.getFactory().getVoipController().unregisterCurrentUser();
    }

    public static void getUnits(){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "units", GET));
    }

    public static void getUnit(int unitId) {
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "units/" + unitId, GET));
    }

    public static void updateOrSaveUnit(Unit unit){
        HttpRequest req = Bootstrapper.getFactory().newHttpRequest(URL + "units", PUT);
        req.addParameter("unit", unit.toString());
        executeRequest(req);
    }

    public static void deleteUnit(int unitId){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "units" + unitId, DELETE));
    }

    public static void getUsers(){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "users", GET));
    }

    public static void getUser(String username){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "users" + username, POST));
    }

    public static void updateUser(User user, String password, boolean changePassword){
        HttpRequest req = Bootstrapper.getFactory().newHttpRequest(URL + "users", PUT);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        req.addParameter("changePassword", String.valueOf(changePassword));
        executeRequest(req);
    }

    public static void addUser(User user, String password){
        HttpRequest req = Bootstrapper.getFactory().newHttpRequest(URL + "users/add", POST);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void deleteUser(int userId){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "users/" + userId, DELETE));
    }

    public static void saveOrUpdateMessage(Message message){
        HttpRequest req = Bootstrapper.getFactory().newHttpRequest(URL + "messages", PUT);
        req.addParameter("message", message.toString());
        executeRequest(req);
    }

    public static void getMessages(int userId1, int userId2, int startIndex, int length){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "messages/" + userId1 + "/" + userId2 + "/" + startIndex + "/" + length, GET));
    }

    public static void getRecentContacts(int userId){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "messages/" + userId, GET));
    }

    public static void getActions(){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "actions", GET));
    }

    public static void getAction(int actionId){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "actions/" + actionId, GET));
    }

    public static void updateOrSaveActions(Action action){
        HttpRequest req = Bootstrapper.getFactory().newHttpRequest(URL + "actions", PUT);
        req.addParameter("action", action.toString());
        executeRequest(req);
    }

    public static void deleteAction(int actionId){
        executeRequest(Bootstrapper.getFactory().newHttpRequest(URL + "actions/" + actionId, DELETE));
    }
    
    private static void executeRequest(HttpRequest request){
        request.setContentType("application/x-www-form-urlencoded");
        if(Bootstrapper.AUTHENTICATION_TOKEN != null)
            request.setHeader(HttpHeadersName.AUTH_TOKEN, Bootstrapper.AUTHENTICATION_TOKEN);
        request.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse resp) {
                if(resp.getStatusCode() != 200)
                    new ErrorEvent(ResBundle.get().errorCode(String.valueOf(resp.getStatusCode()), resp.getContent())).fire();
                else {
                    try {
                        JSONObject object = new JSONObject(resp.getContent());
                        Event.fire(object);
                    } catch (JSONException e) {
                        new ErrorEvent(ResBundle.get().errorUnrecognized(resp.getContent() + " " + e.getMessage())).fire();
                    }
                }
            }

            @Override
            public void onError(String message) {
                new ErrorEvent(ResBundle.get().networkError(message)).fire();
            }
        });
    }


}
