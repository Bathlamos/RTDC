package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.event.FetchActionEvent;
import rtdc.core.exception.ApiException;
import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Permission;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;

import javax.annotation.security.RolesAllowed;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.*;
import java.util.concurrent.TimeUnit;

@WebServlet
@Path("actions")
public class ActionServlet {

    private static final Logger log = LoggerFactory.getLogger(ActionServlet.class);

    @GET
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String get(@Context HttpServletRequest req, @Context User user){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Action> actions = null;
        try{
            transaction = session.beginTransaction();
            actions = (List<Action>) session.createCriteria(Action.class).list();

            // Don't return any actions that are completed and that we're updated more then 12 hours ago
            Date currentDate = new Date();
            for(Iterator<Action> iterator = actions.iterator(); iterator.hasNext();){
                Action action = iterator.next();
                if((action.getStatus() == Action.Status.completed || action.getStatus() == Action.Status.failed)
                        && TimeUnit.MILLISECONDS.toHours(currentDate.getTime() - action.getLastUpdate().getTime()) >= 12){
                    iterator.remove();
                }
            }
            if(Permission.USER.equalsIgnoreCase(User.Permission.getStringifier().toString(user.getPermission()))
                    || Permission.MANAGER.equalsIgnoreCase(User.Permission.getStringifier().toString(user.getPermission())))
                /*actions = (List<Action>) session.createCriteria(Action.class).
                        add(Restrictions.eq("personResponsible", user)).list();*/
                actions = (List<Action>) session.createCriteria(Action.class).
                        add(Restrictions.eq("unit", user.getUnit())).list();
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

    @GET
    @Path("{id}")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String getAction(@Context HttpServletRequest req, @Context User user, @PathParam("id") int id){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        Action action = null;
        try{
            transaction = session.beginTransaction();
            action = (Action) session.get(Action.class, id);
            if(action == null)
                throw new ApiException("Id " + id + " doesn't exist");
            transaction.commit();

            log.info("{}: ACTION: Getting Action " + id + " for user.", user.getUsername());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }

        return new FetchActionEvent(action).toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.MANAGER, Permission.ADMIN})
    public String update(@Context HttpServletRequest req, @Context User user, @FormParam("action")String actionString){
        Action action = new Action(new JSONObject(actionString));

        Set<ConstraintViolation<Action>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(action);
        if(!violations.isEmpty()) {
            log.warn("Error updating action: " + violations.toString());
            return new ErrorEvent(violations.toString()).toString();
        }

        try {
            SimpleValidator.validateAction(action);
        }catch (ValidationException e){
            log.warn("Error updating action: " + e.getMessage());
            return new ErrorEvent(e.getMessage()).toString();
        }

        if(user.getPermission().equals(User.Permission.MANAGER) && user.getUnit().getId() != action.getUnit().getId())
            return new ErrorEvent("Insufficient permissions: you do not have permission to add actions for this unit.").toString();

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            action.setLastUpdate(new Date());
            session.saveOrUpdate(action);
            transaction.commit();

            log.info("{}: ACTION: Action updated: {}", user.getUsername(), actionString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(action.getId(), "action", "update").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed({Permission.MANAGER, Permission.ADMIN})
    public String delete(@Context HttpServletRequest req, @Context User user, @PathParam("id") int id){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Action action = (Action) session.load(Action.class, id);
            session.delete(action);
            transaction.commit();

            log.warn("{}: ACTION: Action deleted: {}", user.getUsername(), action.getId());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(id, "action", "delete").toString();
    }

}
