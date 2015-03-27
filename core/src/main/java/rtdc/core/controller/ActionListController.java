package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.event.Event;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleComparator;
import rtdc.core.service.Service;
import rtdc.core.view.ActionListView;

import java.util.*;

public class ActionListController implements FetchActionsEvent.Handler {

    private ActionListView view;
    private Set<Action> actions;

    public ActionListController(ActionListView view){
        this.view = view;
        Event.subscribe(FetchActionsEvent.TYPE, this);
        Service.getActions();
    }

    public List<Action> sortActions(Action.Properties property){
        LinkedList<Action> sortedActions = new LinkedList<>(actions);
        Collections.sort(sortedActions, SimpleComparator.forProperty(property));
        return sortedActions;
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
