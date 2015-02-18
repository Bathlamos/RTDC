package rtdc.web.server.service;

import org.hibernate.Session;
import rtdc.core.model.Unit;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUnit;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.List;

import static rtdc.core.model.ApplicationRole.ADMIN;
import static rtdc.core.model.ApplicationRole.USER;

@Path("units")
public class UnitService {

    @GET
    @Produces("application/json")
    public List<Unit> getUnits(@Context HttpServletRequest req){
        AuthService.hasRole(req, USER, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Unit> units = (List<Unit>) session.createCriteria(ServerUnit.class).list();
        session.getTransaction().commit();
        return units;
    }

}
