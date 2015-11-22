package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Permission;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;

import javax.annotation.security.RolesAllowed;
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

    private static final Logger log = LoggerFactory.getLogger(UnitServlet.class);

    @GET
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String get(@Context HttpServletRequest req, @Context User user){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Action> actions = null;
        try{
            transaction = session.beginTransaction();
            if(Permission.USER.equalsIgnoreCase(user.getPermission()))
                actions = (List<Action>) session.createCriteria(Action.class).
                        add(Restrictions.eq("personResponsible", user)).list();
            else
                actions = (List<Action>) session.createCriteria(Action.class).list();
            transaction.commit();

            // TODO: Replace string with actual unit name
            log.info("{}: ACTION: Getting actions for unit: {}", user.getUsername(), "Unit name");
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
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String update(@Context HttpServletRequest req, @Context User user, @FormParam("action" )String actionString){
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

            // TODO: Replace string with actual username
            log.info("{}: ACTION: Action updated: {}", user.getUsername(), actionString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(action.getId(), "action", "").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String delete(@Context HttpServletRequest req, @Context User user, @PathParam("id") int id){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Action action = (Action) session.load(Action.class, id);
            session.delete(action);
            transaction.commit();

            // TODO: Replace string with actual username
            log.warn("{}: ACTION: Action deleted: {}", user.getUsername(), action.getId());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(id, "action", "").toString();
    }

}
