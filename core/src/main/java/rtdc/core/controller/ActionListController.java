package rtdc.core.controller;

import rtdc.core.model.Action;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.ActionListView;

import java.util.List;

public class ActionListController {

    private ActionListView view;

    public ActionListController(ActionListView view){
        this.view = view;
        Service.getActions(new AsyncCallback<List<Action>>() {
            @Override
            public void onSuccess(List<Action> actions) {
                ActionListController.this.view.setActions(actions);
            }

            @Override
            public void onError(String message) {
                ActionListController.this.view.displayError("Error", message);
            }
        });
    }

    public void onClickUser(Action action){

    }

    public void onClickNewUser(){

    }



}
