package rtdc.web.server.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import rtdc.core.model.User;
import rtdc.web.server.model.UserFactory;

public class ApiConfig extends ResourceConfig {

    public ApiConfig(){
        packages("rtdc.web.server.servlet;rtdc.web.server.filter");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(UserFactory.class).to(User.class);
            }
        });
    }
}
