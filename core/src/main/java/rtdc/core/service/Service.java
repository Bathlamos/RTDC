package rtdc.core.service;

import rtdc.core.Bootstrapper;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONObject;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.util.Util;

import java.util.LinkedList;
import java.util.List;

import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private static final String URL = "http://192.168.2.49:8888/api/";

    private Service(){}

    public static void authenticateUser(String username, String password, final AsyncCallback<User> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "authenticate", POST);
        req.setHeader("Content-type", "application/x-www-form-urlencoded");
        req.addParameter("username", username);
        req.addParameter("password", password);
        req.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse resp) {
                JsonTransmissionWrapper wrapper = new JsonTransmissionWrapper(resp.getContent());
                if("success".equals(wrapper.getStatus())) {
                    User user = new User();
                    user.map().putAll(wrapper.getData().map());
                    callback.onSuccess(user);
                }else
                    callback.onError(wrapper.getStatus() + " : " + wrapper.getDescription());
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public static void updateOrSaveUser(User user, String password, final AsyncCallback<Boolean> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", PUT);
        req.setHeader("Content-type", "application/x-www-form-urlencoded");
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        req.addParameter("user", user.toString());
        req.addParameter("password", password);
        req.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse resp) {
                JsonTransmissionWrapper wrapper = new JsonTransmissionWrapper(resp.getContent());
                if("success".equals(wrapper.getStatus()))
                    callback.onSuccess(true);
                else
                    callback.onError(wrapper.getStatus() + " : " + wrapper.getDescription());
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public static void getUnits(final AsyncCallback<List<Unit>> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "units", PUT);
        req.setHeader("Content-type", "application/x-www-form-urlencoded");
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        req.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse resp) {
                JsonTransmissionWrapper wrapper = new JsonTransmissionWrapper(resp.getContent());
                if("success".equals(wrapper.getStatus()))
                    callback.onSuccess(Util.asList(wrapper.getDataAsJSONArray(), new LinkedList<Unit>()));
                else
                    callback.onError(wrapper.getStatus() + " : " + wrapper.getDescription());
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public static void getUsers(final AsyncCallback<List<User>> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest(URL + "users", PUT);
        req.setHeader("Content-type", "application/x-www-form-urlencoded");
        req.addParameter("authToken", Bootstrapper.AUTHENTICATION_TOKEN);
        req.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onSuccess(HttpResponse resp) {
                JsonTransmissionWrapper wrapper = new JsonTransmissionWrapper(resp.getContent());
                if("success".equals(wrapper.getStatus()))
                    callback.onSuccess(Util.asList(wrapper.getDataAsJSONArray(), new LinkedList<User>()));
                else
                    callback.onError(wrapper.getStatus() + " : " + wrapper.getDescription());
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

}
