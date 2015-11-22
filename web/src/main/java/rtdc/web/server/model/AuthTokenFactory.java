package rtdc.web.server.model;

import org.glassfish.hk2.api.Factory;
import rtdc.core.model.User;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

public class AuthTokenFactory implements Factory<AuthenticationToken> {

    private final ContainerRequestContext context;

    @Inject
    public AuthTokenFactory(ContainerRequestContext context) {
        this.context = context;
    }

    @Override
    public AuthenticationToken provide() {
        return (AuthenticationToken) context.getProperty("current_auth_token");
    }

    @Override
    public void dispose(AuthenticationToken instance) {

    }

}
