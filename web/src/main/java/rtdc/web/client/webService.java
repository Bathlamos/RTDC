package rtdc.web.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("webService")
public interface webService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    /**
     * Utility/Convenience class.
     * Use webService.App.getInstance() to access static instance of webServiceAsync
     */
    public static class App {
        private static webServiceAsync ourInstance = GWT.create(webService.class);

        public static synchronized webServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
