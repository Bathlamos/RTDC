package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.SessionExpiredEvent;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserCredentials;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("authenticate")
public class AuthService {

    private static final Logger logger = Logger.getLogger("AuthService");

    @POST
    @Path("tokenValid")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String authTokenValid(@Context HttpServletRequest req, @FormParam("authToken") String authenticationToken){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        AuthenticationToken authToken;
        Transaction transaction = null;
        logger.log(Level.INFO, "Received parameter : authToken-" + authenticationToken);
        try{
            transaction = session.beginTransaction();
            authToken = (AuthenticationToken) session.createCriteria(AuthenticationToken.class).add(
                    Restrictions.eq("authToken", authenticationToken)).uniqueResult();
            logger.log(Level.INFO, "Authentication token in database: " + authToken);
            if(authToken == null)
                return new SessionExpiredEvent().toString();
            transaction.commit();
            return new AuthenticationEvent(authToken.getUser(), authToken.getAuthenticationToken()).toString();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

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
            Session session = PersistenceConfig.getSessionFactory().openSession();
            UserCredentials userCredentials = null;
            User user = null;
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();
                user = (User) session.createCriteria(User.class).add(
                        Restrictions.eq("username", username)).uniqueResult();
                userCredentials = (UserCredentials) session.createCriteria(UserCredentials.class).add(
                        Restrictions.eq("user", user)).uniqueResult();
                transaction.commit();
            } catch (RuntimeException e) {
                if(transaction != null)
                    transaction.rollback();
                throw e;
            } finally {
                session.close();
            }

            if(userCredentials == null)
                //We do not have any user with that username in the database
                throw new UsernamePasswordMismatchException("Username / password mismatch");
            else{
                String passwordAttempt = BCrypt.hashpw(password, userCredentials.getSalt());

                if(userCredentials.getPasswordHash().equals(passwordAttempt)) {

                    //Create an authentication token
                    AuthenticationToken token = new AuthenticationToken();
                    token.setDateSet(new Date());
                    token.setAuthenticationToken(BCrypt.gensalt());
                    token.setUser(user);

                    //Store the token
                    session = PersistenceConfig.getSessionFactory().openSession();
                    transaction = null;
                    try{
                        transaction = session.beginTransaction();
                        session.save(token);
                        transaction.commit();
                    } catch (RuntimeException e) {
                        if(transaction != null)
                            transaction.rollback();
                        throw e;
                    } finally {
                        session.close();
                    }

                    return new AuthenticationEvent(user, token.getAuthenticationToken()).toString();
                }else
                    throw new UsernamePasswordMismatchException("Username / password mismatch");
            }
        }
    }

}
