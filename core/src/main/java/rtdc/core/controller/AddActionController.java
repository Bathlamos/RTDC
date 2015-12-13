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

import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.exception.ValidationException;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.util.Stringifier;
import rtdc.core.view.AddActionView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddActionController extends Controller<AddActionView> implements ActionCompleteEvent.Handler {

    private Action RtdcCurrentAction;
    private String currentAction;

    public AddActionController(final AddActionView view){
        super(view);
        Event.subscribe(ActionCompleteEvent.TYPE, this);
        Service.getUnits();

        view.getTaskUiElement().setArray(Action.Task.values());
        view.getTaskUiElement().setStringifier(Action.Task.getStringifier());

        view.getStatusUiElement().setArray(Action.Status.values());
        view.getStatusUiElement().setStringifier(Action.Status.getStringifier());

        view.getUnitUiElement().setArray(new Unit[]{((User)Cache.getInstance().get("sessionUser")).getUnit()});
        view.getUnitUiElement().setStringifier(new Stringifier<Unit>() {
            @Override
            public String toString(Unit unit) {
                return unit == null? "": unit.getName();
            }
        });

        RtdcCurrentAction = (Action) Cache.getInstance().remove("action");
        if (RtdcCurrentAction != null) {
            view.setTitle("Edit Action");
            view.getRoleUiElement().setValue(RtdcCurrentAction.getRoleResponsible());
            view.getTargetUiElement().setValue(RtdcCurrentAction.getTarget());
            view.getDeadlineUiElement().setValue(RtdcCurrentAction.getDeadline());
            view.getDescriptionUiElement().setValue(RtdcCurrentAction.getDescription());
            view.getUnitUiElement().setValue(RtdcCurrentAction.getUnit());
            view.getStatusUiElement().setValue(RtdcCurrentAction.getStatus());
            view.getTaskUiElement().setValue(RtdcCurrentAction.getTask());
        }
    }

    @Override
    String getTitle() {
        return "Add Action";
    }

    public void addAction() {

        if (RtdcCurrentAction != null) {
            currentAction = "edit";
            RtdcCurrentAction.setId(RtdcCurrentAction.getId());
        } else {
            currentAction = "add";
            RtdcCurrentAction = new Action();
        }
        RtdcCurrentAction.setTask(view.getTaskUiElement().getValue());
        RtdcCurrentAction.setRoleResponsible(view.getRoleUiElement().getValue());
        RtdcCurrentAction.setTarget(view.getTargetUiElement().getValue());
        RtdcCurrentAction.setDeadline(view.getDeadlineUiElement().getValue());
        RtdcCurrentAction.setDescription(view.getDescriptionUiElement().getValue());
        RtdcCurrentAction.setStatus(view.getStatusUiElement().getValue());
        RtdcCurrentAction.setUnit(view.getUnitUiElement().getValue());
        RtdcCurrentAction.setLastUpdate(new Date());

        Service.updateOrSaveActions(RtdcCurrentAction);
    }

    public void validateDescription(){
        try{
            SimpleValidator.validateActionDescription(view.getDescriptionUiElement().getValue());
            view.getDescriptionUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getDescriptionUiElement().setErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onActionComplete(ActionCompleteEvent event) {
        if(event.getObjectType().equals("action")){
            if(currentAction.equals("add"))
                RtdcCurrentAction.setId(event.getObjectId());

            Cache.getInstance().put("action", new Pair(currentAction, RtdcCurrentAction));
            view.closeDialog();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(ActionCompleteEvent.TYPE, this);
    }
}
