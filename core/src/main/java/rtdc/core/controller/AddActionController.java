package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.view.AddActionView;

import java.util.ArrayList;

public class AddActionController extends Controller<AddActionView> implements FetchUnitsEvent.Handler{

    private ArrayList<Unit> units = new ArrayList<>();

    public AddActionController(AddActionView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();

        ArrayList<String> tasks = new ArrayList<>();
        for(Action.Task task: Action.Task.values())
            tasks.add(task.name());
        view.getTaskUiElement().setList(tasks);

        ArrayList<String> statuses = new ArrayList<>();
        for(Action.Status status: Action.Status.values())
            statuses.add(status.name());
        view.getStatusUiElement().setList(statuses);
    }

    public void addAction() {

        Action action = new Action();
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
