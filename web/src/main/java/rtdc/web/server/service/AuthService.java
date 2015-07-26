package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.Config;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserCredentials;

import javax.annotation.Nullable;
import java.util.Date;

public class AuthService {

    private AuthService(){}

    public static User getAuthenticatedUser(@Nullable String authToken) {

        if(authToken == null)
            return null;

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

            return authTokenObject.getUser();
        }

        return null;
    }

    public static UserCredentials generateUserCredentials(User user, String password){
        UserCredentials credentials = new UserCredentials();
        credentials.setUser(user);
        credentials.setSalt(BCrypt.gensalt());
        credentials.setPasswordHash(BCrypt.hashpw(password, credentials.getSalt()));
        return credentials;
    }

}
