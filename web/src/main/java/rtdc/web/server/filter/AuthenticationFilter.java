package rtdc.web.server.filter;

import rtdc.core.Bootstrapper;
import rtdc.core.config.Conf;
import rtdc.core.impl.Storage;
import rtdc.core.model.User;
import rtdc.core.service.CookiesName;
import rtdc.core.service.HttpHeadersName;
import rtdc.web.server.model.AuthenticationToken;
import rtdc.web.server.service.AuthService;

import java.io.IOException;
import java.security.Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Except for /login, refuses connections without a valid auth token
 *
 * ref: http://www.developerscrappad.com/1814/java/java-ee/rest-jax-rs/java-ee-7-jax-rs-2-0-simple-rest-api-authentication-authorization-with-custom-http-header/
 */
@Provider
@PreMatching
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);

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
        log.debug("Filtering request path: " + path);

        // IMPORTANT!!! First, Acknowledge any pre-flight test from browsers for this case before validating the headers
        // (CORS stuff)
        if (requestCtx.getRequest().getMethod().equals("OPTIONS")) {
            requestCtx.abortWith(Response.status(Response.Status.OK).build());

            return;
        }

        // For any other methods besides login, the authToken must be verified
        if (!path.startsWith("auth/login") && !path.startsWith("auth/tokenValid")) {
            String authToken = requestCtx.getHeaderString(HttpHeadersName.AUTH_TOKEN);

            // Also check if there are cookies with an authToken

            if (Conf.get().isDebug() && requestCtx.getCookies().containsKey(Storage.KEY_AUTH_TOKEN) && authToken == null)
                authToken = requestCtx.getCookies().get(Storage.KEY_AUTH_TOKEN).getValue();

            log.debug((Conf.get().isDebug()? "Debugging" : "Production") + " with auth token " + authToken);

            // if it isn't valid, just kick them out.
            // This automatically fails if the authentication token is outdated
            AuthenticationToken token = AuthService.getAuthenticationToken(authToken);
            User user = token.getUser();
            requestCtx.setProperty("current_user", user);
            requestCtx.setProperty("current_auth_token", token);

            // Authenticate the user
            requestCtx.setSecurityContext(new SecurityContextImpl(user));
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

        public boolean isUserInRole(String permission) {
            log.info("Checking access rights : " + permission + " / " + user.getPermission());
            return User.Permission.getStringifier().toString(user.getPermission()).equalsIgnoreCase(permission);
        }

        public boolean isSecure() {
            return false;
        }

        public String getAuthenticationScheme() {
            return SecurityContext.BASIC_AUTH;
        }
    }
}