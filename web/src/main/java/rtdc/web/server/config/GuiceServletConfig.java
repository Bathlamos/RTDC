package rtdc.web.server.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {
        return Guice.createInjector( new ServletModule() {
            @Override
            protected void configureServlets() {

                ResourceConfig rc = new PackagesResourceConfig("rtdc.web.server.service");
                for (Class<?> resource : rc.getClasses())
                    bind(resource);

                serve("/api/*").with(GuiceContainer.class);
            }
        } );
    }
}
