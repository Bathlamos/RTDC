package rtdc.core.controller;

import com.google.common.collect.Multimap;
import rtdc.core.Bootstrapper;
import rtdc.core.event.UpdateCompleteEvent;
import rtdc.core.impl.Factory;
import rtdc.core.model.RtdcObject;
import rtdc.core.model.User;
import rtdc.core.service.AsyncCallback;
import rtdc.core.service.Service;
import rtdc.core.view.AddUserView;
import rtdc.core.view.LoginView;

import java.util.Set;

public class AddUserController extends Controller<AddUserView> implements UpdateCompleteEvent.UpdateCompleteHandler {


    public AddUserController(AddUserView view){
        super(view);

        view.getEmailWidget().
    }

    public void addUser() {

        User newUser = new User();
        newUser.setUsername(view.getUsernameAsString());
        newUser.setFirstName(view.getFirstnameAsString());
        newUser.setLastName(view.getSurnameAsString());
        newUser.setEmail(view.getEmailAsString());
        newUser.setPermission(view.getPermissionAsString());
        newUser.setRole(view.getRoleAsString());
        String password = view.getPasswordAsString();

        Multimap<RtdcObject.Property, String> violations = newUser.getConstraintsViolations();
        if(violations.isEmpty())
            Service.updateOrSaveUser(newUser, password);
        else {
            RtdcObject.Property p = violations.keys().iterator().next();
            view.displayError("Error", p.getPropertyName() + " : " + violations.get(p).iterator().next());
        }
    }

    @Override
    public void onUpdateComplete(UpdateCompleteEvent event) {
        view.displayError("Success", "Success");
    }
}
