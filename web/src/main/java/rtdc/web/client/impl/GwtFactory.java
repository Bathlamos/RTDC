package rtdc.web.client.impl;

import rtdc.core.impl.*;

public final class GwtFactory implements Factory {

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new GwtHttpRequest(url, requestMethod);
    }

    @Override
    public Dispatcher newDispatcher() {
        return new GWTDispatcher();
    }

    @Override
    public Storage getStorage() {
        return GwtStorage.get();
    }

    @Override
    public VoipController getVoipController() {
        return GwtVoipController.get();
    }

}
