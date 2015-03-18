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
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.util.Util;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private static final String URL = "http://192.168.2.49:8888/api/";

    private Service(){}

    public static void authenticateUser(String username, String password){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "authenticate", POST);
        req.addParameter("username", username);
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void updateOrSaveUser(User user, String password){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", PUT);
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void updateOrSaveUnit(Unit unit , final AsyncCallback<Boolean> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units", PUT);
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        req.addParameter("unit", unit.toString());
        executeRequest(req);
    }

    public static void getUnits(final AsyncCallback<List<Unit>> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units", GET);
        req.setHeader("Content-type", "application/x-www-form-urlencoded");
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        executeRequest(req);
    }

    public static void getUsers(final AsyncCallback<List<User>> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", GET);
        req.setHeader("Content-type", "application/x-www-form-urlencoded");
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        executeRequest(req);
    }


    private static void executeRequest(HttpRequest request){
        request.setHeader("Content-type", "application/x-www-form-urlencoded");
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
