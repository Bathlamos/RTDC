package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;
import rtdc.core.util.Util;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.UserCredentials;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Set;

import static rtdc.core.model.ApplicationPermission.ADMIN;
import static rtdc.core.model.ApplicationPermission.USER;

@Path("users")
public class UserService {

    @GET
    public String getUsers(@Context HttpServletRequest req){
        //AuthService.hasRole(req, USER, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<User> users = (List<User>) session.createCriteria(UserCredentials.class).list();
            session.getTransaction().commit();
            return new FetchUsersEvent(users).toString();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String updateUser(@Context HttpServletRequest req, @FormParam("password") String password, @FormParam("user" )String userString){
        //AuthService.hasRole(req, ADMIN);
        User user = new User(new JSONObject(userString));

        Set<ConstraintViolation<User>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(user);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        if(password == null || password.isEmpty() || password.length() < 4)
            return new ErrorEvent("Password must be longer than 4 characters").toString();
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            UserCredentials credentials = null;
            if(user.getId() == null)
                credentials = new UserCredentials();
            else
                credentials = (UserCredentials) session.load(UserCredentials.class, user.getId());

            session.saveOrUpdate(user);

            session.getTransaction().commit();
            transaction = session.beginTransaction();

            credentials.setUser(user);
            credentials.setSalt(BCrypt.gensalt());
            credentials.setPasswordHash(BCrypt.hashpw(password, credentials.getSalt()));

            session.saveOrUpdate(credentials);

            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        return new ActionCompleteEvent(user.getId(), "user").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public String deleteUser(@Context HttpServletRequest req, @PathParam("id") String id){
        //AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            User user = (User) session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        return new ActionCompleteEvent(id, "user").toString();
    }

}
