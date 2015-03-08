package rtdc.web.server.service;

import rtdc.core.model.User;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class ResourceAuthorizer implements SecurityContext {

    private User user;
    private Principal principal;

    public ResourceAuthorizer(final User user) {
        this.user = user;
        this.principal = new Principal() {

            @Override
            public String getName() {
                return user.getUsername();
            }
        };
    }

    @Override
    public String getAuthenticationScheme() {
        return SecurityContext.FORM_AUTH;
    }

    @Override
    public Principal getUserPrincipal() {
        return principal;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public boolean isUserInRole(String role) {
        return role.equals(user.getRole());
    }

}