package rtdc.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import rtdc.core.Bootstrapper;
import rtdc.web.client.impl.GwtFactory;
import rtdc.web.client.presenter.AddUserPresenter;
import rtdc.web.client.presenter.LoginPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Rtdc implements EntryPoint{

    public static final String AUTH_TOKEN_KEY = "authTokenKey";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        Bootstrapper.initialize(new GwtFactory());

        RootPanel.get().add(new LoginPresenter());

        RootPanel.get().add(new AddUserPresenter());

    }
}
