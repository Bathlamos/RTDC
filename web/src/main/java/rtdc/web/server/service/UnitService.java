package rtdc.web.server.service;

import com.google.common.collect.ImmutableMultimap;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Unit;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.core.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;
import java.util.Set;

import static rtdc.core.model.ApplicationPermission.ADMIN;
import static rtdc.core.model.ApplicationPermission.USER;

@Path("units")
public class UnitService {

    @GET
    public String getUnits(@Context HttpServletRequest req){
        //AuthService.hasRole(req, USER, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        List<Unit> units = null;
        try{
            transaction = session.beginTransaction();
            units = (List<Unit>) session.createCriteria(Unit.class).list();
            transaction.commit();
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
    public String updateUnit(@Context HttpServletRequest req, @FormParam("unit" )String unitString){
        //AuthService.hasRole(req, ADMIN);
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
    public String deleteUnit(@Context HttpServletRequest req, @PathParam("id") int id){
        // AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Unit unit = (Unit) session.load(Unit.class, id);
            session.delete(unit);
            transaction.commit();
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
