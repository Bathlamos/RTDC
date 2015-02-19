package rtdc.web.server.service;

import com.google.inject.Singleton;
import rtdc.core.exception.ApiException;
import rtdc.core.model.JsonTransmissionWrapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Provider
public class ApiExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable throwable) {
        return Response.status(200).
                entity(new JsonTransmissionWrapper(throwable).toString()).
                type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }

}
