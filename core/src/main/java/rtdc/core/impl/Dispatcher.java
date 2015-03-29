package rtdc.core.impl;

import rtdc.core.view.View;

public interface Dispatcher {

    void goToLogin(boolean crushHistory);
    void goToAllUnits(boolean crushHistory);
    void goToUnitInfo(boolean crushHistory);
}
