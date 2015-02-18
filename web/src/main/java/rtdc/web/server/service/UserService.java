package rtdc.web.server.service;

import org.hibernate.Session;
import rtdc.core.model.User;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUser;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.List;

import static rtdc.core.model.ApplicationPermission.ADMIN;
import static rtdc.core.model.ApplicationPermission.USER;

@Path("users")
public class UserService {

    @GET
    @Produces("application/json")
    public List<User> getUser(@Context HttpServletRequest req){
        AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> users = (List<User>) session.createCriteria(ServerUser.class).list();
        session.getTransaction().commit();
        return users;
    }

    @POST
    @Produces("application/json")
    public boolean updateUser(@Context HttpServletRequest req, ServerUser user){
        AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(user);
        session.getTransaction().commit();
        return true;
    }

    @DELETE
    @Path("{id}")
    @Produces("application/json")
    public boolean deleteUser(@Context HttpServletRequest req, @PathParam("id") String id){
        AuthService.hasRole(req, ADMIN);
        Session session = PersistenceConfig.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ServerUser user = (ServerUser) session.load(ServerUser.class, id);
        session.delete(user);
        session.getTransaction().commit();
        return true;
    }

}
