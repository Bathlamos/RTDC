package rtdc.web.client.impl;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.service.AsyncCallback;

public class GwtHttpRequest implements HttpRequest{

    Request req;

    private String url;
    private RequestMethod requestMethod;
    private String requestData;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute(final AsyncCallback<HttpResponse> callback) {

        RequestBuilder.Method method = null;
        switch(requestMethod){
            case GET: method = RequestBuilder.GET; break;
            case POST: method = RequestBuilder.POST; break;
            case PUT: method = RequestBuilder.PUT; break;
            case DELETE: method = RequestBuilder.DELETE; break;
        }

        RequestBuilder builder = new RequestBuilder(method, url);
        builder.setRequestData(requestData);

        builder.setCallback(new RequestCallback() {
            @Override
            public void onResponseReceived(Request request, Response response) {
                callback.onCallback(new GwtHttpResponse(response));
            }

            @Override
            public void onError(Request request, Throwable exception) {
                Window.alert("Panic");
            }
        });

        try {
            builder.send();
        }catch(RequestException exception){
            exception.printStackTrace();
        }
    }
}
