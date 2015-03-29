package rtdc.android.impl;


import android.content.Intent;
import rtdc.android.AdminActivity;
import rtdc.android.Rtdc;
import rtdc.android.presenter.ActionPlanActivity;
import rtdc.android.presenter.CreateActionActivity;
import rtdc.android.presenter.LoginActivity;
import rtdc.core.impl.Dispatcher;

public class AndroidDispatcher implements Dispatcher {

    @Override
    public void goToLogin() {
        Intent intent = new Intent(Rtdc.getAppContext(), LoginActivity.class);
        Rtdc.getAppContext().startActivity(intent);
    }

    @Override
    public void goToAllUnits() {
        Intent intent = new Intent(Rtdc.getAppContext(), AdminActivity.class);
        Rtdc.getAppContext().startActivity(intent);
    }

    @Override
    public void goToActionPlan() {
        Intent intent = new Intent(Rtdc.getAppContext(), ActionPlanActivity.class);
        Rtdc.getAppContext().startActivity(intent);
    }

    @Override
    public void goToEditAction() {
        Intent intent = new Intent(Rtdc.getAppContext(), CreateActionActivity.class);
        Rtdc.getAppContext().startActivity(intent);
    }
}