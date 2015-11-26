package rtdc.web.client.impl;

import com.google.gwt.core.client.GWT;
import rtdc.core.impl.Dispatcher;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.Storage;
import rtdc.core.impl.VoipController;

public final class GwtFactory implements Factory {

    private static final Config CONFIG = GWT.create(Config.class);

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

    @Override
    public rtdc.core.impl.Config getConfig(){
        return CONFIG;
    }

}
