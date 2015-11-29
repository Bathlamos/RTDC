package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleComparator;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
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
        Bootstrapper.FACTORY.newDispatcher().goToEditAction(this);
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

    // Update edited action when returning from CreateActionActivity
    public void updateActions(){
        Pair<String, Action> pair = (Pair<String, Action>) Cache.getInstance().retrieve("action");
        if(pair != null) {
            String action = pair.getFirst();
            Action updatedAction = pair.getSecond();
            if(action == "add") {
                actions.add(updatedAction);
                sortActions(Action.Properties.task);
            } else if(action == "edit") {
                int actionID = updatedAction.getId();
                int actionCount = actions.size();
                for (int i = 0; i < actionCount; i++) {
                    if (actions.get(i).getId() == actionID) {
                        actions.set(i, updatedAction);
                        sortActions(Action.Properties.task);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchActionsEvent.TYPE, this);
    }
}
