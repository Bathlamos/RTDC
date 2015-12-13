package rtdc.core.impl;

public interface Factory {

    boolean isServer();

    HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod);

    Dispatcher newDispatcher();

    Storage getStorage();

    VoipController getVoipController();

}
