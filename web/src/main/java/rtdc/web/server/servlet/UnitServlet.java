package rtdc.web.server.servlet;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.exception.ApiException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Permission;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("units")
public class UnitServlet {

    private static final Logger log = LoggerFactory.getLogger(UnitServlet.class);

    @GET
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String getUnits(@Context HttpServletRequest req, @Context User user){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Unit> units = null;
        try{
            transaction = session.beginTransaction();
            units = (List<Unit>) session.createCriteria(Unit.class).list();
            transaction.commit();

            log.info("{}: UNIT: Getting all units for user.", user.getUsername());
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally{
            session.close();
        }
        return new FetchUnitsEvent(units).toString();
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String updateUnit(@Context HttpServletRequest req, @Context User user, @FormParam("unit" )String unitString){
        Unit unit = new Unit(new JSONObject(unitString));


        Set<ConstraintViolation<Unit>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(unit);
        if(!violations.isEmpty())
            return new ErrorEvent(violations.toString()).toString();

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.saveOrUpdate(unit);

            transaction.commit();

            log.info("{}: UNIT: Update unit values: {}", user.getUsername(), unitString);
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(unit.getId(), "unit", "update").toString();
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    @RolesAllowed({Permission.USER, Permission.ADMIN})
    public String deleteUnit(@Context HttpServletRequest req, @Context User user, @PathParam("id") int id){
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Unit unit = (Unit) session.load(Unit.class, id);
            if(unit == null)
                throw new ApiException("Id " + id + " doesn't exist");
            transaction.commit();

            log.warn("{}: UNIT: Unit deleted: {}", user.getUsername(), unit.getName());

        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new ActionCompleteEvent(id, "unit", "delete").toString();
    }

}
