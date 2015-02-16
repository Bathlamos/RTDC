package rtdc.web.server.service;

import com.goodow.realtime.json.Json;
import com.google.inject.Singleton;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.AuthenticationInformation;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.User;
import rtdc.web.server.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class Authentication extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AuthenticationInformation authInfo = null;
        try{
            authInfo = new AuthenticationInformation(Util.getHttpRequestData(req));
        }catch(JSONException e){

        }

        //Do some validation with the database

        if(authInfo == null || authInfo.getUsername().equals("Nathaniel")){
            User user = new User();
            user.setFirstName("Nathaniel");
            user.setSurname("Aumonttt");
            user.setId(1);
            resp.getWriter().write(user.toString());
        }else
            ;
            //resp.getWriter().write(
                   // new JsonTransmissionWrapper(new UsernamePasswordMismatchException()).toJsonString());
    }
}
