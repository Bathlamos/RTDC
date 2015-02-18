package rtdc.web.server.service;

import com.google.common.base.Joiner;
import com.google.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;
import rtdc.core.exception.UsernamePasswordMismatchException;
import rtdc.core.json.JSONArray;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.web.server.dao.UserDao;
import rtdc.web.server.model.ServerUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Singleton
public class UnitServlet extends HttpServlet {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String unitIds = req.getParameter("data");
        if(unitIds == null || unitIds.isEmpty())
            userError(resp, "No users provided");
        else {
            JSONArray users = new JSONArray(req.getParameter("data"));

            //Convert the users to AuthenticatedUsers
            List<ServerUser> authUsers = new LinkedList<>();
            for (int i = users.length() - 1; i >= 0; i--) {
                ServerUser user = new ServerUser(users.getString(i));
                //Create a new salt for the user
                user.setSalt(BCrypt.gensalt());
                user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), user.getSalt()));
                authUsers.add(user);

                Set<ConstraintViolation<ServerUser>> violations = VALIDATOR.validate(user);
                if(!violations.isEmpty()){
                    userError(resp, String.format("Malformed user at index %l: %s.", i, Joiner.on(",").join(violations)));
                    return;
                }
            }

            UserDao.persistUsers(authUsers);
            resp.getWriter().write(new JsonTransmissionWrapper("{}").toString());
        }
    }

    private static void userError(HttpServletResponse resp, String message) throws IOException{
        resp.getWriter().write(
                new JsonTransmissionWrapper(new UsernamePasswordMismatchException(message)).toString());
    }
}
