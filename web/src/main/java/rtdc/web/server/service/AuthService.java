package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.exception.InvalidSessionException;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUser;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Path("authenticate")
public class AuthService {

    private static final ConcurrentHashMap<String, UserInformation> authenticatedUsers = new ConcurrentHashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String authenticate(@Context HttpServletRequest req,
                             @FormParam("username") String username,
                             @FormParam("password") String password){

        if(username == null || username.isEmpty())
            throw new UsernamePasswordMismatchException("Username cannot be empty");
        else if (password == null || password.isEmpty())
            throw new UsernamePasswordMismatchException("Password cannot be empty");
        else{
            //Get user by username
            Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
            ServerUser user = null;
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                user = (ServerUser) session.createCriteria(ServerUser.class).add(
                            Restrictions.eq("username", username)).uniqueResult();
                session.getTransaction().commit();
            } catch (RuntimeException e) {
                transaction.rollback();
                throw e;
            }

            if(user == null)
                //We do not have any user with that username in the database
                throw new UsernamePasswordMismatchException("Username / password mismatch");
            else{
                String passwordAttempt = BCrypt.hashpw(password, user.getSalt());

                if(user.getPasswordHash().equals(passwordAttempt)) {

                    //Authenticate user
                    user.setAuthenticationToken(BCrypt.gensalt());
                    UserInformation info = new UserInformation();
                    info.id = user.getId();
                    info.permission = user.getRole();
                    info.lastUsed = new Date();
                    authenticatedUsers.put(user.getAuthenticationToken(), info);

                    return new JsonTransmissionWrapper(user).toString();
                }else
                    throw new UsernamePasswordMismatchException("Username / password mismatch");
            }
        }
    }

    private static final class UserInformation{
        private int id;
        private String permission;
        private Date lastUsed;
    }

    public static boolean hasRole(HttpServletRequest req, String... roles){
        if(roles == null || roles.length == 0)
            return true;
        String token = req.getParameter("auth_token");
        if(token != null && !token.isEmpty()) {
            UserInformation user = authenticatedUsers.remove(token);
            Date now = new Date();
            if(user != null && user.lastUsed.getTime() < now.getTime() + 60 * 60 * 1000){
                user.lastUsed = now;
                return true;
            }
        }
        throw new InvalidSessionException("");
    }

}
