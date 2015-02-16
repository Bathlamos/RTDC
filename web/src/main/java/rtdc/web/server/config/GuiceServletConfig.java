package rtdc.web.server.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import rtdc.web.server.service.Authentication;
import rtdc.web.server.service.ExceptionServlet;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){

            @Override
            protected void configureServlets() {
                serve("/error").with(ExceptionServlet.class);
                serve("/api/authenticate*").with(Authentication.class);
            }
        });
    }
}
