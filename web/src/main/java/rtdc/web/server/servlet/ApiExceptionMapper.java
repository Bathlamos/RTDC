package rtdc.web.server.servlet;

import rtdc.core.event.ErrorEvent;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        throwable.printStackTrace();
        return Response.status(200).
                entity(new ErrorEvent(throwable.getMessage()).toString()).
                type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
