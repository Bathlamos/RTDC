package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.UserCredentials;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Set;

@Path("action")
public class ActionService {

    @GET
    public String getUsers(@Context HttpServletRequest req){
        //AuthService.hasRole(req, USER, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Action> actions = null;
        try{
            transaction = session.beginTransaction();
            actions = (List<Action>) session.createCriteria(Action.class).list();
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }
        return new FetchActionsEvent(actions).toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String updateUser(@Context HttpServletRequest req, @FormParam("action" )String actionString){
        //AuthService.hasRole(req, ADMIN);
        Action action = new Action(new JSONObject(actionString));

        Set<ConstraintViolation<Action>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(action);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            session.saveOrUpdate(action);

            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(action.getId(), "action").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public String deleteUser(@Context HttpServletRequest req, @PathParam("id") int id){
        //AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Action action = (Action) session.load(Action.class, id);
            session.delete(action);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(id, "action").toString();
    }

}
