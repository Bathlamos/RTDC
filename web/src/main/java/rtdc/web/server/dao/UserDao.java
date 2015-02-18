package rtdc.web.server.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import rtdc.web.server.config.PersistenceConfig;
import rtdc.web.server.model.ServerUser;

public class UserDao {

    private static final Session session = PersistenceConfig.getSessionFactory().getCurrentSession();

    public static ServerUser getUserByUsername(String username){
        session.beginTransaction();
        ServerUser user = (ServerUser) session.createCriteria(ServerUser.class).add(
                Restrictions.eq(ServerUser.USERNAME, username)).uniqueResult();
        session.getTransaction().commit();
        return user;
    }

    public static void persistUsers(Iterable<ServerUser> users){
        session.beginTransaction();
        for(ServerUser user: users)
            session.persist(user);
        session.getTransaction().commit();
    }

}
