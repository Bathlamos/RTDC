package rtdc.web.server.service;

import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.exception.InvalidSessionException;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.model.User;
import rtdc.web.server.dao.UserDao;
import rtdc.web.server.model.ServerUser;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

@Path("authenticate")
public class AuthService {

    private static final ConcurrentHashMap<String, UserInformation> authenticatedUsers = new ConcurrentHashMap<>();

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public User authenticate(@Context HttpServletRequest req,
                             @FormParam("username") String username,
                             @FormParam("password") String password){

        if(username == null || username.isEmpty())
            throw new UsernamePasswordMismatchException("Username cannot be empty");
        else if (password == null || password.isEmpty())
            throw new UsernamePasswordMismatchException("Password cannot be empty");
        else{
            ServerUser user = UserDao.getUserByUsername(username);

            if(user == null)
                //We do not have any user with that username in the database
                throw new UsernamePasswordMismatchException("Username / password mismatch");
            else{
                String passwordAttempt = BCrypt.hashpw(password, user.getSalt());

                if(user.getPasswordHash().equals(passwordAttempt)) {

                    //Authenticate user
                    user.setAuthenticationToken(BCrypt.gensalt());
                    UserInformation info = new UserInformation();
                    info.id = user.getId();
                    info.role = user.getRole();
                    info.lastUsed = new Date();
                    authenticatedUsers.put(user.getSalt(), info);

                    return user;

                    //We want some long-term session-like tracking for that individual
                }else
                    throw new UsernamePasswordMismatchException("Username / password mismatch");
            }
        }
    }

    private static final class UserInformation{
        private int id;
        private String role;
        private Date lastUsed;
    }

    public static boolean hasRole(HttpServletRequest req, String... roles){
        if(roles == null || roles.length == 0)
            return true;
        String token = req.getParameter("auth_token");
        if(token != null && !token.isEmpty()) {
            UserInformation user = authenticatedUsers.remove(token);
            Date now = new Date();
            if(user != null && user.lastUsed.getTime() < now.getTime() + 60 * 60 * 1000){
                user.lastUsed = now;
                return true;
            }
        }
        throw new InvalidSessionException("");
    }

}
