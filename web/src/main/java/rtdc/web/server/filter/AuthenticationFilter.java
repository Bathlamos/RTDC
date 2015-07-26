package rtdc.web.server.filter;

import rtdc.core.service.HttpHeadersName;
import rtdc.web.server.service.AuthService;

import java.io.IOException;
import java.util.logging.Logger;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
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
        if (!path.startsWith("/login")) {
            String authToken = requestCtx.getHeaderString(HttpHeadersName.AUTH_TOKEN);

            log.info("With auth token " + authToken);

            // if it isn't valid, just kick them out.
            if (!AuthService.isAuthenticatedFromToken(authToken))
                requestCtx.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}