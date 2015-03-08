package rtdc.core.view;

import rtdc.core.impl.ValidatorWidget;
import rtdc.core.model.Unit;

public interface AddUserView extends View {

    ValidatorWidget<String> getUsernameWidget();
    ValidatorWidget<String> getSurnameWidget();
    ValidatorWidget<String> getFirstNameWidget();
    ValidatorWidget<String> getRoleWidget();
    ValidatorWidget<String> getEmailWidget();
    ValidatorWidget<String> getPasswordWidget();
    ValidatorWidget<String> getSecondPasswordWidget();
    ValidatorWidget<String> getPermissionWidget();
    ValidatorWidget<Unit> getUnitWidget();

}
