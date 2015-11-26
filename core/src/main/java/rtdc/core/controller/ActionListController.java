package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleComparator;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.ActionListView;

import java.util.*;

public class ActionListController extends Controller<ActionListView> implements FetchActionsEvent.Handler {

    private ArrayList<Action> actions;

    public ActionListController(ActionListView view){
        super(view);
        Event.subscribe(FetchActionsEvent.TYPE, this);
        Service.getActions();
    }

    @Override
    String getTitle() {
        return "Action Plan";
    }

    public void sortActions(Action.Properties property){
        Collections.sort(actions, SimpleComparator.forProperty(property).build());
        view.setActions(actions);
    }

    public void editAction(Action action){
        Cache.getInstance().put("action", action);
        Bootstrapper.getFactory().newDispatcher().goToEditAction(this);
    }

    public void saveAction(Action action){
        Service.updateOrSaveActions(action);
    }

    public void deleteAction(Action action) {
        actions.remove(action);
        view.setActions(actions);
        Service.deleteAction(action.getId());
    }

    @Override
    public void onActionsFetched(FetchActionsEvent event) {
        actions = new ArrayList<>(event.getActions());
        sortActions(Action.Properties.task);
    }

    // Return action updated from CreateActionActivity
    public Action getUpdatedAction(){
        return (Action) Cache.getInstance().retrieve("action");
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchActionsEvent.TYPE, this);
    }
}
