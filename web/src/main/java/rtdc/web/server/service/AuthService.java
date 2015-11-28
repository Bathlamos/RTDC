package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.Config;
import rtdc.core.exception.ApiException;
import rtdc.core.exception.SessionExpiredException;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserCredentials;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import java.util.Date;

public class AuthService {

    private AuthService(){}

    public static AuthenticationToken getAuthenticationToken(@Nullable String authToken) {
        if(authToken == null)
            throw new SessionExpiredException("You need to be loged in to access this service");

        Session session = PersistenceConfig.getSessionFactory().openSession();
        AuthenticationToken authTokenObject = null;
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            authTokenObject = (AuthenticationToken) session.createCriteria(AuthenticationToken.class)
                    .add(Restrictions.eq("authToken", authToken)).uniqueResult();
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        //Check the auth token for validity
        Date now = new Date();
        if (authTokenObject != null
                && authTokenObject.getDateSet().getTime() + Config.SESSION_LIFETIME_IN_MS > now.getTime()) {

            return authTokenObject;
        }

        throw new SessionExpiredException("Session expired for auth token " + authTokenObject.getAuthenticationToken());
    }

    public static UserCredentials generateUserCredentials(User user, String password){
        UserCredentials credentials = new UserCredentials();
        credentials.setUser(user);
        credentials.setSalt(BCrypt.gensalt());
        credentials.setPasswordHash(BCrypt.hashpw(password, credentials.getSalt()));
        credentials.setAsteriskPassword(BCrypt.gensalt());
        return credentials;
    }

    /**
     * Changes the password of a given user, provided that the password matches the currently logged in user.
     * This does not log out the user with the updated password.
     *
     * @param req The context of the current request.
     * @param currentPassword The password of the user making the change.
     * @param userId The id of the user for which we update the password.
     * @param newPassword The desired password.
     */
    public static void editPassword(HttpServletRequest req, String currentPassword, int userId, String newPassword){
        User currentUser = (User) req.getSession().getAttribute("current_user");
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            UserCredentials credentials = (UserCredentials) session.createCriteria(UserCredentials.class)
                    .add(Restrictions.eq("userId", currentUser.getId())).uniqueResult();
            if(!isPasswordValid(credentials, currentPassword))
                throw new ApiException("Invalid password");
             credentials = (UserCredentials) session.createCriteria(UserCredentials.class)
                    .add(Restrictions.eq("userId", userId)).uniqueResult();
            credentials.setSalt(BCrypt.gensalt());
            credentials.setPasswordHash(BCrypt.hashpw(newPassword, credentials.getSalt()));
            session.save(credentials);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static boolean isPasswordValid(UserCredentials credentials, String password){
        return BCrypt.hashpw(password, credentials.getSalt()).equals(credentials.getPasswordHash());
    }

}
