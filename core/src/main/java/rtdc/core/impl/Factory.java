package rtdc.core.impl;

import javax.validation.Validator;

public interface Factory {

    HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod);

    Validator newValidator();

    Dispatcher newDispatcher();

}
