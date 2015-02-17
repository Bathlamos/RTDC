package rtdc.core.impl;

import rtdc.core.service.AsyncCallback;

public interface HttpRequest {

    public enum RequestMethod{GET, PUT, DELETE, POST}

    public void setUrl(String url);

    public void setRequestMethod(RequestMethod requestMethod);

    public void setRequestData(String requestData);

    public void execute(AsyncCallback<HttpResponse> response);

}
