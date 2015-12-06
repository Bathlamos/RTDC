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
    @RolesAllowed({Permission.USER, Permission.ADMIN})
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
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String addUser(@Context HttpServletRequest req, @Context User user, @FormParam("user") String userString, @FormParam("password") String password){
        User newUser = new User(new JSONObject(userString));

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
    @RolesAllowed({Permission.USER, Permission.ADMIN})
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

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.merge(editedUser);
            transaction.commit();

            log.info("{}: USER: User updated: {}", user.getUsername(), userString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        if(Boolean.parseBoolean(changePassword) && !password.isEmpty())
            AuthService.editPassword(editedUser, password);

        return new ActionCompleteEvent(user.getId(), "user", "update").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String deleteUser(@Context HttpServletRequest req, @Context User user, @PathParam("id") String idString){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        int id = Integer.valueOf(idString);
        try{
            log.warn("Deleting user with id " + id);
            transaction = session.beginTransaction();
            User userToDelete = (User) session.load(User.class, id);
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