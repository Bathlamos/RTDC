package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.AddUnitView;
import rtdc.core.view.AddUserView;

public class AddUnitController extends Controller<AddUnitView>{

    public AddUnitController(AddUnitView view){
        super(view);
    }

    @Override
    String getTitle() {
        return "Add unit";
    }

    public void addUnit() {

        Unit newUnit = new Unit();
        Unit cachedUnit = (Unit) Cache.getInstance().retrieve("unit");
        if (cachedUnit != null)
            newUnit.setId(cachedUnit.getId());
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
        Bootstrapper.FACTORY.newDispatcher().goToAllUnits(true);
    }

    public void deleteUnit(Unit unit){
        Service.deleteUnit(unit.getId());
    }
}
