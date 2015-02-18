package rtdc.web.client.impl;

import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;

public class GwtFactory implements Factory {

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new GwtHttpRequest(url, requestMethod);
    }

}
