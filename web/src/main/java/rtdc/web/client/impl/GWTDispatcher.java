package rtdc.web.client.impl;

import com.google.gwt.user.client.Window;
import rtdc.core.impl.Dispatcher;

public class GWTDispatcher implements Dispatcher{
    @Override
    public void goToLogin() {
        Window.alert("Going to login");
    }

    @Override
    public void goToAllUnits() {
        Window.alert("Going to units");
    }

    @Override
    public void goToActionPlan() {
        Window.alert("Going to action plan");
    }

    @Override
    public void goToEditAction() {
        Window.alert("Going to edit action");
    }

    @Override
    public void goToUnitInfo() {
        Window.alert("Going to unit");
    }

    @Override
    public void goToCapacityOverview() {
        Window.alert("Going to capacity overview");
    }

}
