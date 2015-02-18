package rtdc.core.service;

import rtdc.core.Bootstrapper;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.User;
import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private static final String URL = "http://127.0.0.1:8888/api/";

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
                if("success".equals(wrapper.getStatus()))
                    callback.onSuccess((User) wrapper.getData());
                else
                    callback.onError(wrapper.getDescription());
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

}
