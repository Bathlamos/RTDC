package rtdc.web.server.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import rtdc.web.server.service.Authentication;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServletModule(){

            @Override
            protected void configureServlets() {
                serve("/authenticate*").with(Authentication.class);
            }
        });
    }
}
