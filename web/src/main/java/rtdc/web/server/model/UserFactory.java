package rtdc.web.server.model;

import org.glassfish.hk2.api.Factory;
import rtdc.core.model.User;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;

public class UserFactory implements Factory<User> {

    private final ContainerRequestContext context;

    @Inject
    public UserFactory(ContainerRequestContext context) {
        this.context = context;
    }

    @Override
    public User provide() {
        return (User) context.getProperty("current_user");
    }

    @Override
    public void dispose(User instance) {

    }

}
