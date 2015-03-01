package rtdc.web.client.impl;

import rtdc.core.impl.Dispatcher;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;

public final class GwtFactory implements Factory {

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new GwtHttpRequest(url, requestMethod);
    }

    @Override
    public Dispatcher newDispatcher() {
        return new GWTDispatcher();
    }

}
