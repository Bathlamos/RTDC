package rtdc.web.server.filter;

import rtdc.core.Config;
import rtdc.core.model.User;
import rtdc.core.service.CookiesName;
import rtdc.core.service.HttpHeadersName;
import rtdc.web.server.service.AuthService;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

/**
 * Except for /login, refuses connections without a valid auth token
 *
 * ref: http://www.developerscrappad.com/1814/java/java-ee/rest-jax-rs/java-ee-7-jax-rs-2-0-simple-rest-api-authentication-authorization-with-custom-http-header/
 */
@Provider
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {

    private final static Logger log = Logger.getLogger(AuthenticationFilter.class.getCanonicalName());

    /**
     *
     * If the request has a valid session token and the user is validated then a
     * user object will be added to the security context
     *
     * Any Resource Controllers can assume the user has been validated and can
     * merely authorize based on the role
     *
     * Resources with @PermitAll annotation do not require an Authorization
     * header but will still be filtered
     *
     * @param requestCtx the ContainerRequest to filter
     *
     */
    @Override
    public void filter(ContainerRequestContext requestCtx) throws IOException {

        String path = requestCtx.getUriInfo().getPath();
        log.info("Filtering request path: " + path);

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers
        // (CORS stuff)
        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());

            return;
        }

        // For any other methods besides login, the authToken must be verified
        if (!path.startsWith("authenticate") && !path.startsWith("open")) {
            String authToken = requestCtx.getHeaderString(HttpHeadersName.AUTH_TOKEN);

            // Also check if there are cookies with an authToken
            if (Config.IS_DEBUG && requestCtx.getCookies().containsKey(CookiesName.AUTH_COOKIE) && authToken == null)
                authToken = requestCtx.getCookies().get(CookiesName.AUTH_COOKIE).getValue();

            log.info("With auth token " + authToken);

            // if it isn't valid, just kick them out.
            User user = AuthService.getAuthenticatedUser(authToken);
            if (user == null)
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            else {
                // Authenticate the user
                requestCtx.setSecurityContext(new SecurityContextImpl(user));
            }
        }
    }

    private static final class SecurityContextImpl implements SecurityContext {


        private final User user;

        public SecurityContextImpl(User user) {
            this.user = user;
        }

        public Principal getUserPrincipal() {
            return new Principal() {

                @Override
                public String getName() {
                    return user.getUsername();
                }

            };
        }

        public boolean isUserInRole(String role) {
            log.info("Checking access rights : " + role + " / " + user.getRole());
            return user.getRole().equalsIgnoreCase(role);
        }

        public boolean isSecure() {
            return false;
        }

        public String getAuthenticationScheme() {
            return SecurityContext.BASIC_AUTH;
        }
    }
}