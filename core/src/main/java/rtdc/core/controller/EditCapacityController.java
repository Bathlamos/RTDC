package rtdc.core.controller;


import rtdc.core.Bootstrapper;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.EditCapacityView;

public class EditCapacityController extends Controller<EditCapacityView>{

    public EditCapacityController(EditCapacityView view) {
        super(view);
    }

    @Override
    String getTitle() {
        return "Edit Capacity - ";
    }

    public void updateCapacity() {
        Unit unit = (Unit) Cache.getInstance().retrieve("unit");

        try {
            unit.setAvailableBeds(Integer.parseInt(view.getAvailableBedsUiElement().getValue()));
            unit.setPotentialDc(Integer.parseInt(view.getPotentialDcUiElement().getValue()));
            unit.setDcByDeadline(Integer.parseInt(view.getDcByDeadlineUiElement().getValue()));
            unit.setTotalAdmits(Integer.parseInt(view.getTotalAdmitsUiElement().getValue()));
            unit.setAdmitsByDeadline(Integer.parseInt(view.getAdmitsByDeadlineUiElement().getValue()));
        }catch(NumberFormatException e){

        }

        /*Set<ConstraintViolation<User>> constraintViolations = Bootstrapper.FACTORY.newValidator().validate(newUser);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<User> first = constraintViolations.iterator().next();
            view.displayError("Error", first.getPropertyPath() + " : " + first.getMessage());
        } else if (password == null || password.isEmpty() || password.length() < 4)
            view.displayError("Error", "Password needs to be at least 4 characters");
        else {*/
        Service.updateOrSaveUnit(unit);
        //}
        Bootstrapper.FACTORY.newDispatcher().goToCapacityOverview(this);
    }
}
