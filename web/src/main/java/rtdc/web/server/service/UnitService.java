package rtdc.web.server.service;

import com.google.common.collect.ImmutableMultimap;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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

        Session session = PersistenceConfig.getSessionFactory().openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();

            Set<ConstraintViolation<Unit>> dbViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(unit);
            if(!dbViolations.isEmpty())
                return new ErrorEvent(dbViolations.toString()).toString();

            session.saveOrUpdate(unit);
            transaction.commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return "whooo!";
        //return new UpdateCompleteEvent(UpdateCompleteEvent.UNIT_UPDATED, unit.getId()).toString();
    }

}
