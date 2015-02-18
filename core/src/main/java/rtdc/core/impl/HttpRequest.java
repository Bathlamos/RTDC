package rtdc.core.impl;

import rtdc.core.service.AsyncCallback;

public interface HttpRequest {

    public enum RequestMethod{GET, PUT, DELETE, POST}

    public void addParameter(String parameter, String data);

    public void setHeader(String name, String value);

    public void execute(AsyncCallback<HttpResponse> response);

}
