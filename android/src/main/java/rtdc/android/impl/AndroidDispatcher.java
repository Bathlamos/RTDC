package rtdc.android.impl;


import android.content.Intent;
import rtdc.android.AdminActivity;
import rtdc.android.Rtdc;
import rtdc.android.presenter.LoginActivity;
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
        Intent intent = new Intent(Rtdc.getAppContext(), AdminActivity.class);
        if(crushHistory)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Rtdc.getAppContext().startActivity(intent);
    }
}