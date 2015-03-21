package rtdc.web.server.service;

import com.google.common.base.Joiner;
import org.apache.log4j.lf5.LogLevel;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.Bootstrapper;
import rtdc.core.exception.InvalidParameterException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.util.Util;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUnit;
import rtdc.web.server.model.ServerUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import static rtdc.core.model.ApplicationPermission.ADMIN;
import static rtdc.core.model.ApplicationPermission.USER;

@Path("users")
public class UserService {

    @GET
    public String getUsers(@Context HttpServletRequest req){
        AuthService.hasRole(req, USER, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<User> users = (List<User>) session.createCriteria(ServerUser.class).list();
            session.getTransaction().commit();
            return new JsonTransmissionWrapper(Util.asJSONArray(users)).toString();
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
        User user = new User(userString);

        if(password == null || password == "" || password.length() < 4)
            throw new InvalidParameterException("Password must be longer than 4 characters");
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            ServerUser sUser = (ServerUser) session.createCriteria(ServerUser.class).add(Restrictions.eq("id", user.getId())).uniqueResult();

            if(sUser == null){
                //We create a new ServerUser
                sUser = new ServerUser(userString);
                sUser.setSalt(BCrypt.gensalt());
                sUser.setPasswordHash(BCrypt.hashpw(password, sUser.getSalt()));
            }else
                sUser.map().putAll(user.map());

            Set<ConstraintViolation<ServerUser>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(sUser);
            if(!violations.isEmpty())
                throw InvalidParameterException.fromConstraintViolations(violations);

            session.saveOrUpdate(sUser);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        return new JsonTransmissionWrapper().toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public String deleteUser(@Context HttpServletRequest req, @PathParam("id") String id){
        AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            ServerUser user = (ServerUser) session.load(ServerUser.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        return new JsonTransmissionWrapper().toString();
    }

}
