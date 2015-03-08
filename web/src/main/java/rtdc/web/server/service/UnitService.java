package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
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

//    @GET
//    public String getUnits(@Context HttpServletRequest req){
//        AuthService.hasRole(req, USER, ADMIN);
//        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
//        Transaction transaction = null;
//        try{
//            transaction = session.beginTransaction();
//            List<Unit> units = (List<Unit>) session.createCriteria(Unit.class).list();
//            session.getTransaction().commit();
//            return new JsonTransmissionWrapper(Util.asJSONArray(units)).toString();
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
//    public String updateUnit(@Context HttpServletRequest req, @FormParam("unit" )String unitString){
//        //AuthService.hasRole(req, ADMIN);
//        Unit unit = new Unit(new JSONObject(unitString));
//
//        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
//        Transaction transaction = null;
//        try{
//            transaction = session.beginTransaction();
//            Unit sUnit = (Unit) session.createCriteria(Unit.class).add(Restrictions.eq("id", unit.getId())).uniqueResult();
//
//            if(sUnit == null){
//                //We create a new ServerUUnit
//                sUnit = new Unit(unitString);
//            }else
//                sUnit.map().putAll(unit.map());
//
//            Set<ConstraintViolation<Unit>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(sUnit);
//            if(!violations.isEmpty())
//                throw InvalidParameterException.fromConstraintViolations(violations);
//
//            session.saveOrUpdate(sUnit);
//            session.getTransaction().commit();
//        } catch (RuntimeException e) {
//            if(transaction != null)
//                transaction.rollback();
//            throw e;
//        }
//        return new JsonTransmissionWrapper().toString();
//    }

}
