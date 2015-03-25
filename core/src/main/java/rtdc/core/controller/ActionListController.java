package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.event.Event;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleComparator;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.ActionListView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ActionListController implements FetchActionsEvent.FetchActionsHandler{

    private ActionListView view;
    private ImmutableSet<Action> actions;

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


    @Override
    public void onActionsFetched(FetchActionsEvent event) {
        actions = event.getActions();
        view.setActions(sortActions(Action.Properties.task));
    }
}
