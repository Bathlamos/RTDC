package rtdc.android.impl;

import android.app.Activity;
import android.content.Intent;
import rtdc.android.AdminActivity;
import rtdc.android.Rtdc;
import rtdc.android.presenter.*;
import rtdc.android.presenter.fragments.AbstractFragment;
import rtdc.core.controller.Controller;
import rtdc.core.impl.Dispatcher;

public class AndroidDispatcher implements Dispatcher {

    @Override
    public void goToLogin(Controller controller) {
        startIntent(LoginActivity.class, controller);
    }

    @Override
    public void goToAllUnits(Controller controller) {
        startIntent(AdminActivity.class, controller);
    }

    @Override
    public void goToActionPlan(Controller controller) {
        startIntent(ActionPlanActivity.class, controller);
    }

    @Override
    public void goToUnitInfo(Controller controller) {
        startIntent(CreateUnitActivity.class, controller);
    }
    
    @Override
    public void goToEditAction(Controller controller) {
        startIntent(CreateActionActivity.class, controller);
    }

    private void startIntent(Class<?> clazz, Controller controller){
        if(controller != null){
            if(controller.getView() instanceof AbstractActivity) {
                AbstractActivity activity = (AbstractActivity) controller.getView();
                activity.startActivity(new Intent(activity, clazz));
            } else if (controller.getView() instanceof AbstractFragment){
                Activity activity = ((AbstractFragment) controller.getView()).getActivity();
                activity.startActivity(new Intent(activity, clazz));
            }
        }else{
            Intent intent = new Intent(Rtdc.getAppContext(), CreateUnitActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Rtdc.getAppContext().startActivity(intent);
        }
    }
}