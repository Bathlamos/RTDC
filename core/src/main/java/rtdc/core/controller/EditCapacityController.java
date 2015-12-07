package rtdc.core.controller;


import rtdc.core.Bootstrapper;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.exception.ValidationException;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.EditCapacityView;

public class EditCapacityController extends Controller<EditCapacityView> implements ActionCompleteEvent.Handler {

    private Unit currentUnit;

    public EditCapacityController(EditCapacityView view) {
        super(view);
        Event.subscribe(ActionCompleteEvent.TYPE, this);
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

        Service.updateOrSaveUnit(currentUnit);

        Cache.getInstance().put("unit", currentUnit);
    }

    public void validateAvailableBedsUiElement(){
        try {
            SimpleValidator.isNumber(view.getAvailableBedsUiElement().getValue());

            Unit u = new Unit();
            u.setAvailableBeds(Integer.parseInt(view.getAvailableBedsUiElement().getValue()));
            u.setTotalBeds(currentUnit.getTotalBeds());
            SimpleValidator.validateAvailableBeds(u);
            view.getAvailableBedsUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getAvailableBedsUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validatePotentialDcUiElement(){
        try {
            SimpleValidator.isNumber(view.getPotentialDcUiElement().getValue());

            Unit u = new Unit();
            u.setPotentialDc(Integer.parseInt(view.getPotentialDcUiElement().getValue()));
            u.setTotalBeds(currentUnit.getTotalBeds());
            SimpleValidator.validatePotentialDc(u);
            view.getPotentialDcUiElement().setErrorMessage(null);

            // Validate the DC by deadline as the potential DC affects its
            validateDcByDeadlineUiElement(false);
        }catch(ValidationException e){
            view.getPotentialDcUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateDcByDeadlineUiElement(boolean validateDependency){
        try {
            if(validateDependency) {
                // Only validate if potential DC is validated
                validatePotentialDcUiElement();
                if (view.getPotentialDcUiElement().getErrorMessage() != null)
                    return;
            }

            SimpleValidator.isNumber(view.getDcByDeadlineUiElement().getValue());

            Unit u = new Unit();
            u.setDcByDeadline(Integer.parseInt(view.getDcByDeadlineUiElement().getValue()));
            u.setPotentialDc(Integer.parseInt(view.getPotentialDcUiElement().getValue()));
            SimpleValidator.validateDcByDeadline(u);
            view.getDcByDeadlineUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getDcByDeadlineUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateTotalAdmitsUiElement(){
        try{
            SimpleValidator.isNumber(view.getTotalAdmitsUiElement().getValue());

            // Validate the admits by deadline as the total admits affects it
            validateAdmitsByDeadlineUiElement(false);
            view.getTotalAdmitsUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getTotalAdmitsUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateAdmitsByDeadlineUiElement(boolean validateDependency){
        try {
            if(validateDependency) {
                // Only validate if total admits is validated
                validateTotalAdmitsUiElement();
                if (view.getTotalAdmitsUiElement().getErrorMessage() != null)
                    return;
            }

            SimpleValidator.isNumber(view.getAdmitsByDeadlineUiElement().getValue());

            Unit u = new Unit();
            u.setAdmitsByDeadline(Integer.parseInt(view.getAdmitsByDeadlineUiElement().getValue()));
            u.setTotalAdmits(Integer.parseInt(view.getTotalAdmitsUiElement().getValue()));
            SimpleValidator.validateAdmitsByDeadline(u);
            view.getAdmitsByDeadlineUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getAdmitsByDeadlineUiElement().setErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onActionComplete(ActionCompleteEvent event) {
        if(event.getObjectType().equals("unit")){
            view.closeDialog();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(ActionCompleteEvent.TYPE, this);
    }
}
