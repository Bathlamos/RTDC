package rtdc.core.service;

import rtdc.core.Bootstrapper;
import rtdc.core.Config;
import rtdc.core.Session;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.Event;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Message;
import rtdc.core.model.Unit;
import rtdc.core.model.User;

import java.util.logging.Logger;

import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private static final String URL = "http://"+ Config.SERVER_IP+":8888/api/";
    private static final Logger logger = Logger.getLogger(Service.class.getCanonicalName());

    private Service(){}

    public static void authenticateUser(String username, String password){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "auth/login", POST);
        req.addParameter("username", username);
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void isAuthTokenValid(){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "auth/tokenValid", POST));
    }

    public static void logout(){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "auth/logout", POST));
        Bootstrapper.FACTORY.getVoipController().unregisterCurrentUser();
    }

    public static void getUnits(){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "units", GET));
    }

    public static void getUnit(int unitId) {
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "units/" + unitId, GET));
    }

    public static void updateOrSaveUnit(Unit unit){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units", PUT);
        req.addParameter("unit", unit.toString());
        executeRequest(req);
    }

    public static void deleteUnit(int unitId){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "units" + unitId, DELETE));
    }

    public static void getUsers(){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "users", GET));
    }

    public static void getUser(String username){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "users" + username, POST));
    }

    public static void updateOrSaveUser(User user, String password){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", PUT);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void deleteUser(int userId){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "users/" + userId, DELETE));
    }

    public static void saveOrUpdateMessage(Message message){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "messages", PUT);
        req.addParameter("message", message.toString());
        executeRequest(req);
    }

    public static void getMessages(int userId1, int userId2, int startIndex, int length){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "messages/" + userId1 + "/" + userId2 + "/" + startIndex + "/" + length, POST));
    }

    public static void getRecentContacts(int userId){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "messages/" + userId, POST));
    }

    public static void getActions(){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "actions", GET));
    }

    public static void getAction(int actionId){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "actions/" + actionId, GET));
    }

    public static void updateOrSaveActions(Action action){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "actions", PUT);
        req.addParameter("action", action.toString());
        executeRequest(req);
    }

    public static void deleteAction(int actionId){
        executeRequest(Bootstrapper.FACTORY.newHttpRequest(URL + "actions/" + actionId, DELETE));
    }
    
    private static void executeRequest(HttpRequest request){
        request.setContentType("application/x-www-form-urlencoded");
        if(Bootstrapper.AUTHENTICATION_TOKEN != null)
            request.setHeader(HttpHeadersName.AUTH_TOKEN, Bootstrapper.AUTHENTICATION_TOKEN);
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
