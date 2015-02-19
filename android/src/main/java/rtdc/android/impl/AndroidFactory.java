package rtdc.android.impl;

import rtdc.core.impl.Dispatcher;
import rtdc.core.impl.Factory;
import rtdc.core.impl.HttpRequest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class AndroidFactory implements Factory{

    @Override
    public HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod) {
        return new AndroidHttpRequest(url, requestMethod);
    }

    @Override
    public Validator newValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Override
    public Dispatcher newDispatcher() {
        return new AndroidDispatcher();
    }
}
