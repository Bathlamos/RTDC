package rtdc.web.server.service;

import com.google.common.collect.ImmutableMultimap;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import rtdc.core.event.ErrorEvent;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.event.UpdateCompleteEvent;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Property;
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
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<Unit> units = (List<Unit>) session.createCriteria(Unit.class).list();
            session.getTransaction().commit();
            return new FetchUnitsEvent(units.toArray(new Unit[units.size()])).toString();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
    }

    @PUT
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public String updateUnit(@Context HttpServletRequest req, @FormParam("unit" )String unitString){
        //AuthService.hasRole(req, ADMIN);
        Unit unit = new Unit(new JSONObject(unitString));
        Unit serverUnit = null;

        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            serverUnit = (Unit) session.createCriteria(Unit.class).add(Restrictions.eq("id", unit.getId())).uniqueResult();
            Unit newUnit = new Unit(new JSONObject(unitString));

            ImmutableMultimap<Property, String> violations = newUnit.getConstraintsViolations();
            if(!violations.isEmpty())
                return new ErrorEvent(violations.toString()).toString();

            if(serverUnit == null) //We create a new ServerUUnit
                serverUnit = newUnit;
            else //We copy the properties
                serverUnit.copyProperties(newUnit);

            Set<ConstraintViolation<Unit>> dbViolations = Validation.buildDefaultValidatorFactory().getValidator().validate(serverUnit);
            if(!dbViolations.isEmpty())
                return new ErrorEvent(dbViolations.toString()).toString();

            session.saveOrUpdate(serverUnit);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if(transaction != null)
                transaction.rollback();
            throw e;
        }
        return new UpdateCompleteEvent(UpdateCompleteEvent.UNIT_UPDATED, serverUnit.getId()).toString();
    }

}
