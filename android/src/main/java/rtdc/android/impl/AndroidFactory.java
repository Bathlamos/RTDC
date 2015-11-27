package rtdc.android.impl;

import rtdc.core.impl.*;

public class AndroidFactory implements Factory{

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new AndroidHttpRequest(url, requestMethod);
    }

    @Override
    public Dispatcher newDispatcher() {
        return new AndroidDispatcher();
    }

    @Override
    public Storage getStorage() {
        return AndroidStorage.get();
    }

    @Override
    public VoipController getVoipController() {
        return AndroidVoipController.get();
    }
}
