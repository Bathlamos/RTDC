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

public class UnitListController extends Controller<UnitListView>{

    public UnitListController(UnitListView view){
        super(view);
        Service.getUnits();
    }

    public void onClickUser(Unit unit){

    }

    public void onClickNewUser(){

    }



}
