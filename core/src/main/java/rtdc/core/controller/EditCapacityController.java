package rtdc.core.controller;


import rtdc.core.Bootstrapper;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.EditCapacityView;

public class EditCapacityController extends Controller<EditCapacityView>{

    private Unit currentUnit;

    public EditCapacityController(EditCapacityView view) {
        super(view);
        currentUnit = (Unit) Cache.getInstance().retrieve("unit");

        //Populate the fields
        view.setTitle("Edit Capacity - " + currentUnit.getName());
        view.getAvailableBedsUiElement().setValue(Integer.toString(currentUnit.getAvailableBeds()));
        view.getPotentialDcUiElement().setValue(Integer.toString(currentUnit.getPotentialDc()));
        view.getDcByDeadlineUiElement().setValue(Integer.toString(currentUnit.getDcByDeadline()));
        view.getTotalAdmitsUiElement().setValue(Integer.toString(currentUnit.getTotalAdmits()));
        view.getAdmitsByDeadlineUiElement().setValue(Integer.toString(currentUnit.getAdmitsByDeadline()));
    }

    @Override
    String getTitle() {
        return "Edit Capacity";
    }

    public void updateCapacity() {
        try {
            currentUnit.setAvailableBeds(Integer.parseInt(view.getAvailableBedsUiElement().getValue()));
            currentUnit.setPotentialDc(Integer.parseInt(view.getPotentialDcUiElement().getValue()));
            currentUnit.setDcByDeadline(Integer.parseInt(view.getDcByDeadlineUiElement().getValue()));
            currentUnit.setTotalAdmits(Integer.parseInt(view.getTotalAdmitsUiElement().getValue()));
            currentUnit.setAdmitsByDeadline(Integer.parseInt(view.getAdmitsByDeadlineUiElement().getValue()));
        }catch(NumberFormatException e){

        }

        /*Set<ConstraintViolation<User>> constraintViolations = Bootstrapper.FACTORY.newValidator().validate(newUser);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<User> first = constraintViolations.iterator().next();
            view.displayError("Error", first.getPropertyPath() + " : " + first.getMessage());
        } else if (password == null || password.isEmpty() || password.length() < 4)
            view.displayError("Error", "Password needs to be at least 4 characters");
        else {*/
        Service.updateOrSaveUnit(currentUnit);
        //}
        view.closeDialog();
    }
}
