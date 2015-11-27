package rtdc.core.service;

import rtdc.core.Bootstrapper;
import rtdc.core.config.Conf;
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

import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private final static String BASE_URL = Conf.get().apiProtocol() + "://" +
            Conf.get().apiHost() + ":" +
            Conf.get().apiPort() +
            Conf.get().apiPath();

    private Service(){}

    public static void authenticateUser(String username, String password){
        HttpRequest req = getRequest("auth/login", POST);
        req.addParameter("username", username);
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void isAuthTokenValid(){
        executeRequest(getRequest("auth/tokenValid", POST));
    }

    public static void logout(){
        executeRequest(getRequest("auth/logout", POST));
        Bootstrapper.getFactory().getVoipController().unregisterCurrentUser();
    }

    public static void getUnits(){
        executeRequest(getRequest("units", GET));
    }

    public static void getUnit(int unitId) {
        executeRequest(getRequest("units/" + unitId, GET));
    }

    public static void updateOrSaveUnit(Unit unit){
        HttpRequest req = getRequest("units", PUT);
        req.addParameter("unit", unit.toString());
        executeRequest(req);
    }

    public static void deleteUnit(int unitId){
        executeRequest(getRequest("units" + unitId, DELETE));
    }

    public static void getUsers(){
        executeRequest(getRequest("users", GET));
    }

    public static void getUser(String username){
        executeRequest(getRequest("users" + username, POST));
    }

    public static void updateOrSaveUser(User user, String password){
        HttpRequest req = getRequest("users", PUT);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        executeRequest(req);
    }

    public static void deleteUser(int userId){
        executeRequest(getRequest("users/" + userId, DELETE));
    }

    public static void saveOrUpdateMessage(Message message){
        HttpRequest req = getRequest("messages", PUT);
        req.addParameter("message", message.toString());
        executeRequest(req);
    }

    public static void getMessages(int userId1, int userId2, int startIndex, int length){
        executeRequest(getRequest("messages/" + userId1 + "/" + userId2 + "/" + startIndex + "/" + length, POST));
    }

    public static void getRecentContacts(int userId){
        executeRequest(getRequest("messages/" + userId, POST));
    }

    public static void getActions(){
        executeRequest(getRequest("actions", GET));
    }

    public static void updateOrSaveActions(Action action){
        HttpRequest req = getRequest("actions", PUT);
        req.addParameter("action", action.toString());
        executeRequest(req);
    }

    public static void deleteAction(int actionId){
        executeRequest(getRequest("actions/" + actionId, DELETE));
    }
    
    private static HttpRequest getRequest(String url, HttpRequest.RequestMethod method) {
        return Bootstrapper.getFactory().newHttpRequest(BASE_URL + url, method);
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
