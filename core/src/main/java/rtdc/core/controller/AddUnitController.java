package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.ActionCompleteEvent;
import rtdc.core.event.Event;
import rtdc.core.exception.ValidationException;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.Unit;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.view.AddUnitView;

public class AddUnitController extends Controller<AddUnitView> implements ActionCompleteEvent.Handler {

    private Unit currentUnit;

    public AddUnitController(AddUnitView view){
        super(view);
        Event.subscribe(ActionCompleteEvent.TYPE, this);

        currentUnit = (Unit) Cache.getInstance().retrieve("unit");
        if (currentUnit != null) {
            view.setTitle("Edit Unit");
            view.getNameUiElement().setValue(currentUnit.getName());
            view.getTotalBedsUiElement().setValue(Integer.toString(currentUnit.getTotalBeds()));
        } else {
            view.hideDeleteButton();
        }
    }

    @Override
    String getTitle() {
        return "Add unit";
    }

    public void addUnit() {
        Unit newUnit = new Unit();
        String action = "add";
        if (currentUnit != null) {
            newUnit = new Unit(currentUnit.toJsonObject());
            action = "edit";
        }
        newUnit.setName(view.getNameUiElement().getValue());

        try {
            int newTotal = Integer.parseInt(view.getTotalBedsUiElement().getValue());
            if (newTotal != newUnit.getTotalAdmits()) {
                newUnit.setTotalBeds(newTotal);
                newUnit.setAdmitsByDeadline(0);
                newUnit.setAvailableBeds(0);
                newUnit.setDcByDeadline(0);
                newUnit.setPotentialDc(0);
                newUnit.setTotalAdmits(0);
            }
        }catch(NumberFormatException e){}

        Service.updateOrSaveUnit(newUnit);

        Cache.getInstance().put("unit", new Pair(action, newUnit));
    }

    public void deleteUnit(){
        Cache.getInstance().put("unit", new Pair("delete", currentUnit));
        if (currentUnit != null)
            Service.deleteUnit(currentUnit.getId());
    }

    public void validateUnitNameUiElement(){
        try {
            SimpleValidator.validateUnitName(view.getNameUiElement().getValue());
            view.getNameUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getNameUiElement().setErrorMessage(e.getMessage());
        }
    }

    public void validateTotalBedsUiElement(){
        try {
            SimpleValidator.isNumber(view.getTotalBedsUiElement().getValue());
            view.getTotalBedsUiElement().setErrorMessage(null);
        }catch(ValidationException e){
            view.getTotalBedsUiElement().setErrorMessage(e.getMessage());
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
