package rtdc.core.controller;

import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.AddUnitView;
import rtdc.core.view.AddUserView;

public class AddUnitController {

    private AddUnitView view;

    public AddUnitController(AddUnitView view){
        this.view = view;
    }

    public void addUnit() {

        Unit newUnit = new Unit();
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
            Service.updateOrSaveUnit(newUnit, new AsyncCallback<Boolean>() {

                @Override
                public void onSuccess(Boolean result) {
                    view.displayError("Success", "Success");
                }

                @Override
                public void onError(String message) {
                    view.displayError("CommError", message);
                }
            });
        //}
    }
}
