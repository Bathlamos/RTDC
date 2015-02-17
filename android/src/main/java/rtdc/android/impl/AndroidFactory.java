package rtdc.android.impl;

import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;

/**
 * Created by Nicolas on 2015-02-16.
 */
public class AndroidFactory implements Factory{

    @Override
    public HttpRequest newHttpRequest() {
        return new AndroidHttpRequest();
    }
}
