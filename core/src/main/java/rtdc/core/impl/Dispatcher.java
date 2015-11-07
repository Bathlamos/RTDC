package rtdc.core.impl;

import rtdc.core.controller.Controller;

public interface Dispatcher {

    void goToLogin(Controller caller);
    void goToManageUnits(Controller caller);
    void goToManageUsers(Controller caller);
    void goToActionPlan(Controller caller);
    void goToEditUser(Controller caller);
    void goToEditUnit(Controller caller);
    void goToEditAction(Controller caller);
    void goToEditCapacity(Controller caller);
    void goToCapacityOverview(Controller caller);

}
