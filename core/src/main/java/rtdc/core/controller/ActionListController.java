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
        Pair<String, Action> pair = (Pair<String, Action>) Cache.getInstance().remove("action");
        if(pair != null) {
            String action = pair.getFirst();
            Action updatedAction = pair.getSecond();
            if(action.equals("add")) {
                actions.add(updatedAction);
                sortActions(Action.Properties.task);
            } else if(action.equals("edit")) {
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
