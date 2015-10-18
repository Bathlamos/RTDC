package rtdc.core.controller;

import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Stringifier;
import rtdc.core.view.AddActionView;

import java.util.ArrayList;
import java.util.Arrays;

public class AddActionController extends Controller<AddActionView> implements FetchUnitsEvent.Handler{

    private ArrayList<Unit> units = new ArrayList<>();

    private Action currentAction;

    public AddActionController(AddActionView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();

        view.getTaskUiElement().setArray(Action.Task.values());
        view.getTaskUiElement().setStringifier(Action.Task.getStringifier());

        view.getStatusUiElement().setArray(Action.Status.values());
        view.getStatusUiElement().setStringifier(Action.Status.getStringifier());

        view.getUnitUiElement().setStringifier(new Stringifier<Unit>() {
            @Override
            public String toString(Unit unit) {
                return unit == null? "": unit.getName();
            }
        });

        currentAction = (Action) Cache.getInstance().retrieve("action");
        if (currentAction != null) {
            view.setTitle("Edit Action");
            view.getRoleUiElement().setValue(currentAction.getRoleResponsible());
            view.getTargetUiElement().setValue(currentAction.getTarget());
            view.getDeadlineUiElement().setValue(currentAction.getDeadline());
            view.getDescriptionUiElement().setValue(currentAction.getDescription());
            view.getUnitUiElement().setValue(currentAction.getUnit());
            view.getStatusUiElement().setValue(currentAction.getStatus());
            view.getTaskUiElement().setValue(currentAction.getTask());
        }
    }

    @Override
    String getTitle() {
        return "Add Action";
    }

    public void addAction() {

        Action action = new Action();
        if (currentAction != null)
            action.setId(currentAction.getId());
        action.setTask(view.getTaskUiElement().getValue());
        action.setRoleResponsible(view.getRoleUiElement().getValue());
        action.setTarget(view.getTargetUiElement().getValue());
        action.setDeadline(view.getDeadlineUiElement().getValue());
        action.setDescription(view.getDescriptionUiElement().getValue());
        action.setStatus(view.getStatusUiElement().getValue());
        action.setUnit(units.get(view.getUnitUiElement().getSelectedIndex()));

        /*Set<ConstraintViolation<User>> constraintViolations = Bootstrapper.FACTORY.newValidator().validate(newUser);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<User> first = constraintViolations.iterator().next();
            view.displayError("Error", first.getPropertyPath() + " : " + first.getMessage());
        } else if (password == null || password.isEmpty() || password.length() < 4)
            view.displayError("Error", "Password needs to be at least 4 characters");
        else {*/
        Service.updateOrSaveActions(action);
        //}
        view.closeDialog();
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        view.getUnitUiElement().setArray(event.getUnits().toArray(new Unit[event.getUnits().size()]));
        units = new ArrayList<>(event.getUnits());
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchUnitsEvent.TYPE, this);
    }
}
