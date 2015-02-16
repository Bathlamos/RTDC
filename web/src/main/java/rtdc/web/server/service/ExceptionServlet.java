package rtdc.web.server.service;

import com.google.inject.Singleton;
import rtdc.core.model.JsonTransmissionWrapper;
import rtdc.core.model.Unit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class ExceptionServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if(throwable != null) {
            JsonTransmissionWrapper wrapper = new JsonTransmissionWrapper(throwable);
            resp.getWriter().write(wrapper.toJsonString());
        }
    }
}
