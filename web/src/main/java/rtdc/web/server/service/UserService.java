package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
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
//
//    @GET
//    public String getUsers(@Context HttpServletRequest req){
//        AuthService.hasRole(req, USER, ADMIN);
//        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
//        Transaction transaction = null;
//        try{
//            transaction = session.beginTransaction();
//            List<User> users = (List<User>) session.createCriteria(UserCredentials.class).list();
//            session.getTransaction().commit();
//            return new JsonTransmissionWrapper(Util.asJSONArray(users)).toString();
//        } catch (RuntimeException e) {
//            if(transaction != null)
//                transaction.rollback();
//            throw e;
//        }
//    }
//
//    @PUT
//    @Consumes("application/x-www-form-urlencoded")
//    @Produces("application/json")
//    public String updateUser(@Context HttpServletRequest req, @FormParam("password") String password, @FormParam("user" )String userString){
//        //AuthService.hasRole(req, ADMIN);
//        User user = new User(userString);
//
//        if(password == null || password.isEmpty() || password.length() < 4)
//            throw new InvalidParameterException("Password must be longer than 4 characters");
//        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
//        Transaction transaction = null;
//        try{
//            transaction = session.beginTransaction();
//            UserCredentials sUser = (UserCredentials) session.createCriteria(UserCredentials.class).add(Restrictions.eq("id", user.getId())).uniqueResult();
//
//            if(sUser == null){
//                //We create a new ServerUser
//                sUser = new UserCredentials(userString);
//                sUser.setSalt(BCrypt.gensalt());
//                sUser.setPasswordHash(BCrypt.hashpw(password, sUser.getSalt()));
//            }else
//                sUser.map().putAll(user.map());
//
//            Set<ConstraintViolation<UserCredentials>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(sUser);
//            if(!violations.isEmpty())
//                throw InvalidParameterException.fromConstraintViolations(violations);
//
//            session.saveOrUpdate(sUser);
//            session.getTransaction().commit();
//        } catch (RuntimeException e) {
//            if(transaction != null)
//                transaction.rollback();
//            throw e;
//        }
//        return new JsonTransmissionWrapper().toString();
//    }
//
//    @DELETE
//    @Path("{id}")
//    @Produces("application/json")
//    public String deleteUser(@Context HttpServletRequest req, @PathParam("id") String id){
//        AuthService.hasRole(req, ADMIN);
//        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
//        Transaction transaction = null;
//        try{
//            transaction = session.beginTransaction();
//            UserCredentials user = (UserCredentials) session.load(UserCredentials.class, id);
//            session.delete(user);
//            session.getTransaction().commit();
//        } catch (RuntimeException e) {
//            if(transaction != null)
//                transaction.rollback();
//            throw e;
//        }
//        return new JsonTransmissionWrapper().toString();
//    }

}
