package rtdc.core.service;

import com.goodow.realtime.json.Json;
import rtdc.core.Bootstrapper;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONException;
import rtdc.core.model.AuthenticationInformation;
import rtdc.core.model.User;
import static rtdc.core.impl.HttpRequest.RequestMethod.*;

public final class Service {

    private static final String URL = "localhost:8888/api/";

    private Service(){}

    public static void authenticateUser(AuthenticationInformation authInfo, final AsyncCallback<User> callback){
        HttpRequest req = Bootstrapper.FACTORY.newHttpRequest();

        req.setUrl(URL + "authenticate");
        req.setRequestData(authInfo.toString());
        req.setRequestMethod(POST);
        req.execute(new AsyncCallback<HttpResponse>() {
            @Override
            public void onCallback(HttpResponse resp) {
                callback.onCallback(new User(resp.getContent()));
            }
        });
    }

}
