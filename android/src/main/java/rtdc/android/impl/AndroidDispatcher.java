package rtdc.android.impl;


import android.content.Intent;
import rtdc.android.CapacityOverviewActivity;
import rtdc.android.MyActivity;
import rtdc.android.Rtdc;
import rtdc.android.presenter.LoginActivity;
import rtdc.core.Bootstrapper;
import rtdc.core.impl.Dispatcher;

public class AndroidDispatcher implements Dispatcher {

    @Override
    public void goToLogin(boolean crushHistory) {
        Intent intent = new Intent(Rtdc.getAppContext(), LoginActivity.class);
        if(crushHistory)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Rtdc.getAppContext().startActivity(intent);
    }

    @Override
    public void goToAllUnits(boolean crushHistory) {
        Intent intent = new Intent(Rtdc.getAppContext(), MyActivity.class);
        if(crushHistory)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Rtdc.getAppContext().startActivity(intent);
    }
}