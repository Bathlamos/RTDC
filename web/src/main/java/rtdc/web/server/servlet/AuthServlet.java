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

package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.config.Conf;
import rtdc.core.event.AuthenticationEvent;
import rtdc.core.event.LogoutEvent;
import rtdc.core.event.SessionExpiredEvent;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.model.Permission;
import rtdc.core.model.User;
import rtdc.core.service.CookiesName;
import rtdc.core.service.HttpHeadersName;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserCredentials;
import rtdc.web.server.service.AuthService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("auth")
public class AuthServlet {

    private static final Logger log = LoggerFactory.getLogger(AuthServlet.class);

    @POST
    @Path("tokenValid")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String authTokenValid(@Context HttpServletRequest req){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        String authenticationToken = req.getHeader(HttpHeadersName.AUTH_TOKEN);
        AuthenticationToken authToken;
        Transaction transaction = null;
        UserCredentials userCredentials = null;

        log.debug("Received parameter : authToken = {}", authenticationToken);
        try{
            transaction = session.beginTransaction();
            authToken = (AuthenticationToken) session.createCriteria(AuthenticationToken.class).add(
                    Restrictions.eq("authToken", authenticationToken)).uniqueResult();
            log.debug("Authentication token in database: {}", authToken);
            if(authToken == null)
                return new SessionExpiredEvent().toString();
            transaction.commit();
            userCredentials = (UserCredentials) session.createCriteria(UserCredentials.class).add(
                    Restrictions.eq("user", authToken.getUser())).uniqueResult();
            return new AuthenticationEvent(authToken.getUser(), authToken.getAuthenticationToken(), userCredentials.getAsteriskPassword()).toString();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
    public String authenticate(@Context HttpServletRequest req,
                               @Context HttpServletResponse resp,
                               @FormParam("username") String username,
                               @FormParam("password") String password){

        username = username.toLowerCase();

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
                        Restrictions.ilike("username", username)).uniqueResult();
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

            if(userCredentials == null) {
                //We do not have any user with that username in the database
                log.warn("{}: LOGIN FAILED: User does not exist.", username);
                throw new UsernamePasswordMismatchException("Username / password mismatch");
            }else{
                if(AuthService.isPasswordValid(userCredentials, password)) {

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
                        log.info("{}: LOGIN SUCCESSFUL: User is connected.", username);
                    } catch (RuntimeException e) {
                        if(transaction != null)
                            transaction.rollback();
                        throw e;
                    } finally {
                        session.close();
                    }

                    req.getSession().setAttribute("current_user", token.getUser());

                    // So that we can test the api in the browser
                    if (Conf.get().isDebug())
                        resp.addCookie(new Cookie(CookiesName.AUTH_COOKIE, token.getAuthenticationToken()));
                    
                    return new AuthenticationEvent(user, token.getAuthenticationToken(), userCredentials.getAsteriskPassword()).toString();
                }else
                    log.warn("{}: LOGIN FAILED: Invalid password.", username);
                    throw new UsernamePasswordMismatchException("Username / password mismatch");
            }
        }
    }

    @POST
    @Path("logout")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String logout(@Context HttpServletRequest req, @Context User user, @Context AuthenticationToken token){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.delete(token);
            transaction.commit();
            log.info("{}: LOGOUT: User has been disconnected.", user.getUsername());
            return new LogoutEvent().toString();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

}
