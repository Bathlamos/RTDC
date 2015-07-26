package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.web.server.config.PersistenceConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Set;

@WebServlet
@Path("actions")
public class ActionServlet {

    @GET
    public String get(@Context HttpServletRequest req){
        //AuthServlet.hasRole(req, USER, ADMIN);
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
    public String update(@Context HttpServletRequest req, @FormParam("action" )String actionString){
        //AuthServlet.hasRole(req, ADMIN);
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
    public String delete(@Context HttpServletRequest req, @PathParam("id") int id){
        //AuthServlet.hasRole(req, ADMIN);
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
