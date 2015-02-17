package rtdc.web.server.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import rtdc.web.server.service.AuthenticationServlet;
import rtdc.web.server.service.ExceptionServlet;
import rtdc.web.server.service.UserServlet;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){

            @Override
            protected void configureServlets() {
                serve("/error").with(ExceptionServlet.class);
                serve("/api/authenticate*").with(AuthenticationServlet.class);
                serve("/api/user*").with(UserServlet.class);
            }
        });
    }
}
