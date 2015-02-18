package rtdc.android.impl;

import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;

public class AndroidFactory implements Factory{

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new AndroidHttpRequest(url, requestMethod);
    }
}
