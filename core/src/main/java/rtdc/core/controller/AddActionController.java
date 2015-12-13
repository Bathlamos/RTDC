package rtdc.core.controller;

import rtdc.core.Session;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.exception.ValidationException;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.util.Stringifier;
import rtdc.core.view.AddActionView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddActionController extends Controller<AddActionView> implements FetchUnitsEvent.Handler, ActionCompleteEvent.Handler {

    private ArrayList<Unit> units = new ArrayList<>();

    private Action RtdcCurrentAction;
    private String currentAction;

    public AddActionController(final AddActionView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Event.subscribe(ActionCompleteEvent.TYPE, this);
        Service.getUnits();

        view.getTaskUiElement().setArray(Action.Task.values());
        view.getTaskUiElement().setStringifier(Action.Task.getStringifier());

        view.getStatusUiElement().setArray(Action.Status.values());
        view.getStatusUiElement().setStringifier(Action.Status.getStringifier());

        view.getUnitUiElement().setArray(new Unit[]{Session.getCurrentSession().getUser().getUnit()});
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
        RtdcCurrentAction.setUnit(units.get(view.getUnitUiElement().getSelectedIndex()));
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
    public void onUnitsFetched(FetchUnitsEvent event) {
        //view.getUnitUiElement().setArray(event.getUnits().toArray(new Unit[event.getUnits().size()]));
        units = new ArrayList<>(event.getUnits());
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
        Event.unsubscribe(FetchUnitsEvent.TYPE, this);
        Event.unsubscribe(ActionCompleteEvent.TYPE, this);
    }
}
