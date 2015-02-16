package rtdc.web.server.service;

import com.google.inject.Singleton;
import org.hibernate.Session;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.AuthenticationInformation;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class Authentication extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AuthenticationInformation authInfo = new AuthenticationInformation(Util.getHttpRequestData(req));

        //Do some validation with the database

        if(authInfo == null || authInfo.getUsername().equals("Nathaniel")){
            User user = new User();
            user.setFirstName("Nathaniel");
            user.setSurname("Aumonttt");
            user.setId(1);
            resp.getWriter().write(user.toString());

            //Get Session
            Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
            //start transaction
            session.beginTransaction();
            //Save the Model object
            session.save(user);
            //Commit transaction
            session.getTransaction().commit();
            System.out.println("User ID="+user.getId());

            //terminate session factory, otherwise program won't end
            PersistenceConfig.getSessionFactory().close();

        }else
            resp.getWriter().write(
                   new JsonTransmissionWrapper(new UsernamePasswordMismatchException()).toString());
    }
}
