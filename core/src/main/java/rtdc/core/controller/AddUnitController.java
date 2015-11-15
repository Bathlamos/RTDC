package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.model.Unit;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.view.AddUnitView;

public class AddUnitController extends Controller<AddUnitView>{

    private Unit currentUnit;

    public AddUnitController(AddUnitView view){
        super(view);

        currentUnit = (Unit) Cache.getInstance().retrieve("unit");
        if (currentUnit != null) {
            view.setTitle("Edit Unit");
            view.setNameAsString(currentUnit.getName());
            view.setTotalBedsAsString(Integer.toString(currentUnit.getTotalBeds()));
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
            newUnit.setId(currentUnit.getId());
            action = "edit";
        }
        newUnit.setName(view.getNameAsString());
        try {
            newUnit.setTotalBeds(Integer.parseInt(view.getTotalBedsAsString()));
        }catch(NumberFormatException e){}


        /*Set<ConstraintViolation<User>> constraintViolations = Bootstrapper.FACTORY.newValidator().validate(newUser);

        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<User> first = constraintViolations.iterator().next();
            view.displayError("Error", first.getPropertyPath() + " : " + first.getMessage());
        } else if (password == null || password.isEmpty() || password.length() < 4)
            view.displayError("Error", "Password needs to be at least 4 characters");
        else {*/
            Service.updateOrSaveUnit(newUnit);
        //}
        Cache.getInstance().put("unit", new Pair(action, newUnit));
        view.closeDialog();
    }

    public void deleteUnit(){
        Cache.getInstance().put("unit", new Pair("delete", currentUnit));
        if (currentUnit != null)
            Service.deleteUnit(currentUnit.getId());
        view.closeDialog();
    }
}
