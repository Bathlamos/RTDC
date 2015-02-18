package rtdc.web.client.impl;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.service.AsyncCallback;

public class GwtHttpRequest implements HttpRequest{

    private final RequestBuilder builder;
    private StringBuilder params = new StringBuilder();

    public GwtHttpRequest(String url, RequestMethod requestMethod){
        RequestBuilder.Method method = null;
        switch(requestMethod){
            case GET: method = RequestBuilder.GET; break;
            case POST: method = RequestBuilder.POST; break;
            case PUT: method = RequestBuilder.PUT; break;
            case DELETE: method = RequestBuilder.DELETE; break;
        }
        builder = new RequestBuilder(method, url);
    }

    public void setHeader(String name, String value){
        builder.setHeader(name, value);
    }


    @Override
    public void addParameter(String parameter, String data) {
        if(params.length() != 0)
            params.append('&');
        params.append(parameter);
        params.append('=');
        params.append(data);
    }

    @Override
    public void execute(final AsyncCallback<HttpResponse> callback) {
        try {
            builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    callback.onSuccess(new GwtHttpResponse(response));
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onError(exception.getMessage());
                }
            });
        }catch(RequestException exception){
            exception.printStackTrace();
        }
    }
}
