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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchUserEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Permission;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserCredentials;
import rtdc.web.server.service.AsteriskRealTimeService;
import rtdc.web.server.service.AuthService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Path("users")
public class UserServlet {

    private static final Logger log = LoggerFactory.getLogger(UserServlet.class);

    @GET
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String getUsers(@Context HttpServletRequest req){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<User> users = null;
        try{
            transaction = session.beginTransaction();
            users = (List<User>) session.createCriteria(User.class).list();
            transaction.commit();

            // TODO: Replace string with actual username
            log.info("{}: USER: Getting all users for user.", "Username");
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }
        return new FetchUsersEvent(users).toString();
    }

    @GET
    @Path("{username}")
    @Consumes("application/x-www-form-urlencoded")
    // Currently users with permission USER must have access to this cause it is used to get information for communications
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String getUser(@Context HttpServletRequest req, @Context User user, @PathParam("username") String username){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        User retrievedUser = null;
        try{
            transaction = session.beginTransaction();
            List<User> userList = (List<User>) session.createCriteria(User.class).add(Restrictions.eq("username", username)).list();
            if(!userList.isEmpty())
                retrievedUser = userList.get(0);
            else
                return new ErrorEvent("No user with username " + username + " found.").toString();
            transaction.commit();

            log.info("{}: USER: Getting user " + retrievedUser.getUsername() + " for user.", user.getUsername());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }
        return new FetchUserEvent(retrievedUser).toString();
    }

    @POST
    @Path("add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.ADMIN})
    public String addUser(@Context HttpServletRequest req, @Context User user, @FormParam("user") String userString, @FormParam("password") String password){
        User newUser = new User(new JSONObject(userString));
        user.setUsername(user.getUsername().toLowerCase());
        Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(newUser);
        if(!violations.isEmpty()) {
            log.warn("Error adding user: " + violations.toString());
            return new ErrorEvent(violations.toString()).toString();
        }

        try {
            SimpleValidator.validateUser(newUser);
            SimpleValidator.validatePassword(password);
        }catch (ValidationException e){
            log.warn("Error adding user: " + e.getMessage());
            return new ErrorEvent(e.getMessage()).toString();
        }

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(newUser);
            UserCredentials credentials = AuthService.generateUserCredentials(newUser, password);
            session.saveOrUpdate(credentials);
            AsteriskRealTimeService.addUser(newUser, credentials.getAsteriskPassword());
            transaction.commit();

            log.info("{}: USER: New user added: {}", user.getUsername(), userString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return new ActionCompleteEvent(newUser.getId(), "user", "add").toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String editUser(@Context HttpServletRequest req, @Context User user, @FormParam("user") String userString, @FormParam("password") String password, @FormParam("changePassword") String changePassword){
        User editedUser = new User(new JSONObject(userString));

        Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(editedUser);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        try {
            SimpleValidator.validateUser(editedUser);
        }catch (ValidationException e){
            log.warn("Error editing user: " + e.getMessage());
            return new ErrorEvent(e.getMessage()).toString();
        }

        if(!user.getPermission().equals(User.Permission.ADMIN)){
            if(user.getId() != editedUser.getId()) {
                log.warn("Error editing user: user " + user.getUsername() + " doesn't have enough permissions");
                return new ErrorEvent("Insufficient permissions: you do not have permission to modify this user.").toString();
            }else if(!user.getPermission().equals(editedUser.getPermission()) || !user.getUnit().equals(editedUser.getUnit())
                    || !user.getRole().equals(editedUser.getRole())){
                log.warn("Error editing user: user " + user.getUsername() + " tried to change its own permission, unit or role");
                return new ErrorEvent("Insufficient permissions: to edit your permissions, unit or role, please talk to an admin.").toString();
            }
        }

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(editedUser);
            AsteriskRealTimeService.editUser(editedUser);
            transaction.commit();

            log.info("{}: USER: User updated: {}", user.getUsername(), userString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        if(Boolean.parseBoolean(changePassword)) {
            try {
                SimpleValidator.validatePassword(password);
                AuthService.editPassword(editedUser, password);
            }catch (ValidationException e){
                log.warn("Error editing user's password: " + e.getMessage());
                return new ErrorEvent(e.getMessage()).toString();
            }
        }

        return new ActionCompleteEvent(user.getId(), "user", "update").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed({Permission.ADMIN})
    public String deleteUser(@Context HttpServletRequest req, @Context User user, @PathParam("id") String idString){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        int id = Integer.valueOf(idString);
        try{
            log.warn("Deleting user with id " + id);
            transaction = session.beginTransaction();
            User userToDelete = (User) session.load(User.class, id);

            List<UserCredentials> userCredentialsList = (List<UserCredentials>) session.createCriteria(UserCredentials.class).add(Restrictions.eq("user", userToDelete)).list();
            if(!userCredentialsList.isEmpty()){
                session.delete(userCredentialsList.get(0));
            }

            List<AuthenticationToken> authenticationTokenList = (List<AuthenticationToken>) session.createCriteria(AuthenticationToken.class).add(Restrictions.eq("user", userToDelete)).list();
            if(!authenticationTokenList.isEmpty()){
                session.delete(authenticationTokenList.get(0));
            }

            session.delete(userToDelete);
            AsteriskRealTimeService.deleteUser(userToDelete);
            transaction.commit();

            log.warn("{}: USER: User deleted: {}", user.getUsername(), userToDelete.getUsername());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(id, "user", "delete").toString();
    }

}