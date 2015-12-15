/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.android.impl;

import android.app.Activity;
import android.content.Intent;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.presenter.*;
import rtdc.android.presenter.fragments.AbstractFragment;
import rtdc.android.presenter.fragments.ActionPlanFragment;
import rtdc.android.presenter.fragments.CapacityOverviewFragment;
import rtdc.core.controller.Controller;
import rtdc.core.impl.Dispatcher;

/**
 * Used to navigate from one part of the application to the other
 */
public class AndroidDispatcher implements Dispatcher {

    @Override
    public void goToLogin(Controller caller) {
        startIntent(LoginActivity.class, caller);
    }

    @Override
    public void goToManageUnits(Controller caller) {
        startIntent(MainActivity.class, caller, 3);
    }

    @Override
    public void goToManageUsers(Controller caller) {
        startIntent(MainActivity.class, caller, 4);
    }

    @Override
    public void goToActionPlan(Controller caller) {
        startIntent(MainActivity.class, caller, 1);
    }

    @Override
    public void goToCapacityOverview(Controller caller) {
        startIntent(MainActivity.class, caller, 0);
    }

    @Override
    public void goToEditUser(Controller caller) {
        startIntent(CreateUserActivity.class, caller);
    }

    @Override
    public void goToEditUnit(Controller caller) {
        startIntent(CreateUnitActivity.class, caller);
    }

    @Override
    public void goToEditAction(Controller caller) {
        startIntent(CreateActionActivity.class, caller);
    }

    @Override
    public void goToEditCapacity(Controller caller) {
        startIntent(EditCapacityActivity.class, caller);
    }

    private void startIntent(Class<?> clazz, Controller caller){
        if(caller != null){
            if(caller.getView() instanceof AbstractActivity) {
                AbstractActivity activity = (AbstractActivity) caller.getView();
                activity.startActivity(new Intent(activity, clazz));
            } else if (caller.getView() instanceof AbstractFragment){
                Activity activity = ((AbstractFragment) caller.getView()).getActivity();
                activity.startActivity(new Intent(activity, clazz));
            }
        }else{
            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), clazz);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AndroidBootstrapper.getAppContext().startActivity(intent);
        }
    }

    private void startIntent(Class<?> clazz, Controller caller, int fragmentID){
        if(caller != null){
            if(caller.getView() instanceof AbstractActivity) {
                AbstractActivity activity = (AbstractActivity) caller.getView();
                Intent intent = new Intent(activity, clazz);
                intent.putExtra("fragment", fragmentID);
                activity.startActivity(intent);
            } else if (caller.getView() instanceof AbstractFragment) {
                Activity activity = ((AbstractFragment) caller.getView()).getActivity();
                Intent intent = new Intent(activity, clazz);
                intent.putExtra("fragment", fragmentID);
                activity.startActivity(intent);
            }
        }else{
            Intent intent = new Intent(AndroidBootstrapper.getAppContext(), clazz);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fragment", fragmentID);
            AndroidBootstrapper.getAppContext().startActivity(intent);
        }
    }
}