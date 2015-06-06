package rtdc.android.impl;

import rtdc.core.impl.Dispatcher;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.Storage;

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
}
