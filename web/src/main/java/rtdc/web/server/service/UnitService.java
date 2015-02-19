package rtdc.web.server.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.Unit;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUnit;
import rtdc.core.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;

import static rtdc.core.model.ApplicationPermission.ADMIN;
import static rtdc.core.model.ApplicationPermission.USER;

@Path("units")
public class UnitService {

    @GET
    public String getUnits(@Context HttpServletRequest req){
        AuthService.hasRole(req, USER, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<Unit> units = (List<Unit>) session.createCriteria(ServerUnit.class).list();
            session.getTransaction().commit();
            return new JsonTransmissionWrapper(Util.asJSONArray(units)).toString();
        } catch (RuntimeException e) {
            transaction.rollback();
            throw e;
        }
    }

}
