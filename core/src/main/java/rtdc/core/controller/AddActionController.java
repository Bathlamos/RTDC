package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.AddActionView;

import java.util.ArrayList;

public class AddActionController extends Controller<AddActionView> implements FetchUnitsEvent.Handler{

    private ArrayList<Unit> units = new ArrayList<>();

    private Action currentAction;

    public AddActionController(AddActionView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();

        //TODO: Switching over an enum in a for is particularly atrocious.
        ArrayList<String> tasks = new ArrayList<>();
        for(Action.Task task: Action.Task.values()) {
            switch(task){
                case holdFor: tasks.add("Hold"); break;
                case offServicingTo: tasks.add("Off servicing"); break;
                case pushForDischarge: tasks.add("Push for discharge"); break;
            }
        }
        view.getTaskUiElement().setList(tasks);

        ArrayList<String> statuses = new ArrayList<>();
        for(Action.Status status: Action.Status.values()) {
            switch(status){
                case notStarted: statuses.add("Not started"); break;
                case inProgress: statuses.add("In progress"); break;
                case completed: statuses.add("Completed"); break;
                case failed: statuses.add("Failed"); break;
            }
        }
        view.getStatusUiElement().setList(statuses);

        currentAction = (Action) Cache.getInstance().retrieve("action");
        if (currentAction != null) {
            view.setTitle("Edit Action");
            view.getRoleUiElement().setValue(currentAction.getRoleResponsible());
            view.getTargetUiElement().setValue(currentAction.getTarget());
            view.getDeadlineUiElement().setValue(currentAction.getDeadline());
            view.getDescriptionUiElement().setValue(currentAction.getDescription());
            view.getUnitUiElement().setValue(currentAction.getUnit().getName());
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
        Bootstrapper.FACTORY.newDispatcher().goToActionPlan(this);
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = new ArrayList<>(event.getUnits());
        ArrayList<String> unitNames = new ArrayList<>(units.size());

        for(Unit unit: units)
            unitNames.add(unit.getName());

        view.getUnitUiElement().setList(unitNames);
    }
}
