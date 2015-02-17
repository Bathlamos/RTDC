package rtdc.web.server.service;

import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.AuthenticationInformation;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.User;
import rtdc.web.server.auth.AuthenticatedUser;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.SecureRandom;

@Singleton
public class AuthenticationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        AuthenticationInformation authInfo = new AuthenticationInformation(Util.getHttpRequestData(req));
        String username = authInfo.getUsername();
        String password = authInfo.getPassword();


        if(username == null || username.isEmpty())
            authError(resp, "Username cannot be empty");
        else if (password == null || password.isEmpty())
            authError(resp, "Password cannot be empty");
        else{
            //Get a user by username
            Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            AuthenticatedUser user = (AuthenticatedUser) session.createCriteria(AuthenticatedUser.class).add(
                    Restrictions.eq(AuthenticatedUser.USERNAME, username)).uniqueResult();
            session.getTransaction().commit();

            if(user == null)
                //We do not have any user with that username in the database
                authError(resp, "Username / password mismatch");
            else{
                String passwordAttempt = BCrypt.hashpw(password, user.getSalt());

                if(user.getPasswordHash().equals(passwordAttempt)) {
                    //Our user is authenticated
                    resp.getWriter().write(user.toString());

                    //We want some long-term session-like tracking for that individual
                }else
                    authError(resp, "Username / password mismatch");
            }
        }
    }

    private static void authError(HttpServletResponse resp, String message) throws IOException{
        resp.getWriter().write(
                new JsonTransmissionWrapper(new UsernamePasswordMismatchException(message)).toString());
    }
}
