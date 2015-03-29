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

    private Set<Action> actions;

    public ActionListController(ActionListView view){
        super(view);
        Event.subscribe(FetchActionsEvent.TYPE, this);
        Service.getActions();
    }

    @Override
    String getTitle() {
        return "Action Plan";
    }

    public List<Action> sortActions(Action.Properties property){
        LinkedList<Action> sortedActions = new LinkedList<>(actions);
        Collections.sort(sortedActions, SimpleComparator.forProperty(property));
        return sortedActions;
    }

    public void editAction(Action action){
        Cache.getInstance().put("action", action);
        Bootstrapper.FACTORY.newDispatcher().goToEditAction(this);
    }

    public void deleteAction(Action action) {
        actions.remove(action);
        Service.deleteAction(action.getId());
    }

    @Override
    public void onActionsFetched(FetchActionsEvent event) {
        actions = new HashSet<>(event.getActions());
        view.setActions(sortActions(Action.Properties.task));
    }
}
