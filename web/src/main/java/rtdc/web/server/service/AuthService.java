/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.config.Conf;
import rtdc.core.exception.ApiException;
import rtdc.core.exception.SessionExpiredException;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserCredentials;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class AuthService {

    private AuthService(){}

    public static AuthenticationToken getAuthenticationToken(@Nullable String authToken) {
        if(authToken == null)
            throw new SessionExpiredException("You need to be logged in to access this service");

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
                && authTokenObject.getDateSet().getTime() + Conf.get().sessionLifetime() > now.getTime()) {

            return authTokenObject;
        }

        throw new SessionExpiredException("Session expired");
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
     * @param user The user who wants to change his password.
     * @param password The new password.
     */
    public static void editPassword(User user, String password){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            UserCredentials credentials = (UserCredentials) session.createCriteria(UserCredentials.class)
                    .add(Restrictions.eq("user", user)).uniqueResult();
            credentials.setSalt(BCrypt.gensalt());
            credentials.setPasswordHash(BCrypt.hashpw(password, credentials.getSalt()));
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
