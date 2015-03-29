package rtdc.web.client.impl;

import com.google.gwt.user.client.Window;
import rtdc.core.impl.Dispatcher;

public class GWTDispatcher implements Dispatcher{
    @Override
    public void goToLogin(boolean crushHistory) {
        Window.alert("Going to login");
    }

    @Override
    public void goToAllUnits(boolean crushHistory) {
        Window.alert("Going to units");
    }

    @Override
    public void goToUnitInfo(boolean crushHistory) {
        Window.alert("Going to unit");
    }
}
