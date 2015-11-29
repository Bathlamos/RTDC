package rtdc.web.server.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import rtdc.core.model.User;
import rtdc.web.server.model.AuthTokenFactory;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.model.UserFactory;

import java.util.logging.Logger;

public class ApiConfig extends ResourceConfig {

    private static final Logger LOGGER = Logger.getLogger(ApiConfig.class.getSimpleName());

    public ApiConfig(){
        packages("rtdc.web.server.servlet;rtdc.web.server.filter");


        register(RolesAllowedDynamicFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bindFactory(UserFactory.class).to(User.class);
                bindFactory(AuthTokenFactory.class).to(AuthenticationToken.class);
            }
        });

        LOGGER.info("API Config loaded.");
    }
}
