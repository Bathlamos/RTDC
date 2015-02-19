package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.LoginView;
import rtdc.core.view.UnitListView;
import rtdc.core.view.UserListView;

import java.util.List;

public class UnitListController {

    private UnitListView view;

    public UnitListController(UnitListView view){
        this.view = view;
        Service.getUnits(new AsyncCallback<List<Unit>>() {
            @Override
            public void onSuccess(List<Unit> units) {
                UnitListController.this.view.setUnits(units);
            }

            @Override
            public void onError(String message) {
                UnitListController.this.view.displayError("Error", message);
            }
        });
    }

    public void onClickUser(Unit unit){

    }

    public void onClickNewUser(){

    }



}
