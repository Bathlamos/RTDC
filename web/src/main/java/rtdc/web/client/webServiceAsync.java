package rtdc.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface webServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
}
