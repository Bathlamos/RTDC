package rtdc.web.server.service;

import org.hibernate.Session;
import rtdc.core.model.Unit;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUnit;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;

import static rtdc.core.model.ApplicationPermission.ADMIN;
import static rtdc.core.model.ApplicationPermission.USER;

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

    @POST
    @Produces("application/json")
    public boolean updateUnit(@Context HttpServletRequest req, ServerUnit unit){
        AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(unit);
        session.getTransaction().commit();
        return true;
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public boolean deleteUnit(@Context HttpServletRequest req, @PathParam("id") String id){
        AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ServerUnit unit = (ServerUnit) session.load(ServerUnit.class, id);
        session.delete(unit);
        session.getTransaction().commit();
        return true;
    }

}
