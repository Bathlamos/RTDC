package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.event.Event;
import rtdc.core.event.FetchActionsEvent;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.view.AddActionView;
import rtdc.core.view.AddUnitView;

public class AddActionController extends Controller<AddActionView> implements FetchUnitsEvent.Handler{

    private ImmutableSet<Unit> units = ImmutableSet.of();

    public AddActionController(AddActionView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();
    }

    public void addAction() {

        Action action = new Action();
        action.setTask(view.getActionAsString());


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
        units = event.getUnits();
    }
}
