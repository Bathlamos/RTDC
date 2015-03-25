package rtdc.core.controller;

import rtdc.core.event.FetchActionsEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.view.AddActionView;
import rtdc.core.view.AddUnitView;

public class AddActionController extends Controller<AddActionView>{

    public AddActionController(AddActionView view){
        super(view);
    }

    public void addUnit() {

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
}
