package rtdc.web.server.service;

import com.google.inject.Singleton;
import rtdc.core.model.Unit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class Authentication extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Unit unit = new Unit();
        unit.setName("Alistairs' \" Unit");

        resp.getWriter().write(unit.toJsonString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Unit unit = new Unit();
        unit.setName("Alistairs' \" Unit");

        resp.getWriter().write(unit.toJsonString());

    }
}
