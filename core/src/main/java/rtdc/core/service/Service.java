package rtdc.core.service;

import rtdc.core.Bootstrapper;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.exception.SessionExpiredException;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.util.Util;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private static final String URL = "http://192.168.2.25:8888/api/";

    private Service(){}

    public static void authenticateUser(String username, String password){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "authenticate", POST);
        req.addParameter("username", username);
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void getUnits(){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units", GET);
        executeRequest(req);
    }

    public static void updateOrSaveUnit(Unit unit){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units", PUT);
        req.addParameter("unit", unit.toString());
        executeRequest(req);
    }

    public static void deleteUnit(int unitId){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units/" + unitId, DELETE);
        req.addParameter("id", unitId + "");
        executeRequest(req);
    }

    public static void getUsers(){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", GET);
        executeRequest(req);
    }

    public static void updateOrSaveUser(User user, String password){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", PUT);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void deleteUser(int userId){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users/" + userId, DELETE);
        req.addParameter("id", userId + "");
        executeRequest(req);
    }

    public static void getActions(){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "actions", GET);
        executeRequest(req);
    }

    public static void updateOrSaveActions(Action action){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "actions", PUT);
        req.addParameter("action", action.toString());
        executeRequest(req);
    }

    public static void deleteAction(int actionId){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "actions/" + actionId, DELETE);
        executeRequest(req);
    }
    
    private static void executeRequest(HttpRequest request){
        request.setContentType("application/x-www-form-urlencoded");
        //TODO:         req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        request.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse resp) {
                if(resp.getStatusCode() != 200)
                    new ErrorEvent("Error code " + resp.getStatusCode() + " " + resp.getContent()).fire();
                else {
                    try {
                        JSONObject object = new JSONObject(resp.getContent());
                        Event.fire(object);
                    } catch (JSONException e) {
                        new ErrorEvent("Unrecognized output from server " + resp.getContent() + " " + e.getMessage()).fire();
                    }
                }
            }

            @Override
            public void onError(String message) {
                new ErrorEvent("Network error " + message).fire();
            }
        });
    }


}
