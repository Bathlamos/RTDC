package rtdc.core.impl;

public interface Factory {

    HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod);

    Dispatcher newDispatcher();

}
