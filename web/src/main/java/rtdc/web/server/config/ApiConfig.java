package rtdc.web.server.config;

import org.glassfish.jersey.server.ResourceConfig;
import rtdc.web.server.servlet.UserServlet;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ApiConfig extends Application {}
