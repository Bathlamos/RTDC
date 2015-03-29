package rtdc.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.*;
import rtdc.core.Bootstrapper;
import rtdc.core.view.BootstrapperView;
import rtdc.web.client.impl.GwtFactory;
import rtdc.web.client.presenter.AddUserPresenter;
import rtdc.web.client.presenter.LoginPresenter;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Rtdc implements EntryPoint, BootstrapperView{

    public static final String AUTH_TOKEN_KEY = "authTokenKey";

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        Bootstrapper.initialize(new GwtFactory(), this);

        RootPanel.get().add(new LoginPresenter());

        RootPanel.get().add(new AddUserPresenter());

    }

    @Override
    public void saveAuthenticationToken(String authToken) {
        Cookies.setCookie(AUTH_TOKEN_KEY, authToken);
    }

    @Override
    public String getAuthenticationToken() {
        return Cookies.getCookie(AUTH_TOKEN_KEY);
    }
}
