package rtdc.web.client.impl;

import rtdc.core.impl.Dispatcher;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.Storage;
import rtdc.core.impl.VoipController;

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
