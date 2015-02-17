package rtdc.web.client.impl;


import com.google.gwt.http.client.Response;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.service.AsyncCallback;

public class GwtHttpResponse implements HttpResponse{

    private Response response;

    public GwtHttpResponse(Response response){
        this.response = response;
    }

    @Override
    public int getStatusCode() {
        return response.getStatusCode();
    }

    @Override
    public String getContent() {
        return response.getText();
    }
}
