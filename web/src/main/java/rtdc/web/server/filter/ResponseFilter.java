package rtdc.web.server.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rtdc.core.service.HttpHeadersName;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@PreMatching
public class ResponseFilter implements ContainerResponseFilter {

    private static final Logger log = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public void filter(ContainerRequestContext requestCtx, ContainerResponseContext responseCtx) throws IOException {

        log.debug("Filtering REST Response");

        responseCtx.getHeaders().add("Access-Control-Allow-Origin", "*");    // You may further limit certain client IPs with Access-Control-Allow-Origin instead of '*'
        responseCtx.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseCtx.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        responseCtx.getHeaders().add("Access-Control-Allow-Headers", HttpHeadersName.AUTH_TOKEN);
    }
}