package rtdc.web.server.service;

import com.google.common.base.Joiner;
import com.google.inject.Singleton;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.json.JSONArray;
import rtdc.core.model.AuthenticationInformation;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.web.server.auth.AuthenticatedUser;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Singleton
public class UserServlet extends HttpServlet {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String usersAsString = req.getParameter("data");
        if(usersAsString == null || usersAsString.isEmpty())
            userError(resp, "No users provided");
        else {
            JSONArray users = new JSONArray(req.getParameter("data"));

            //Convert the users to AuthenticatedUsers
            List<AuthenticatedUser> authUsers = new LinkedList<>();
            for (int i = users.length() - 1; i >= 0; i--) {
                AuthenticatedUser user = new AuthenticatedUser(users.getString(i));
                //Create a new salt for the user
                user.setSalt(BCrypt.gensalt());
                user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), user.getSalt()));
                authUsers.add(user);

                Set<ConstraintViolation<AuthenticatedUser>> violations = VALIDATOR.validate(user);
                if(!violations.isEmpty()){
                    userError(resp, String.format("Malformed user at index %l: %s.", i, Joiner.on(",").join(violations)));
                    return;
                }
            }

            //Persist the users
            Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            for(AuthenticatedUser user: authUsers)
                session.persist(user);
            session.getTransaction().commit();

            resp.getWriter().write(new JsonTransmissionWrapper("{}").toString());
        }
    }

    private static void userError(HttpServletResponse resp, String message) throws IOException{
        resp.getWriter().write(
                new JsonTransmissionWrapper(new UsernamePasswordMismatchException(message)).toString());
    }
}
