package rtdc.core.view;

import rtdc.core.impl.UiElement;
import rtdc.core.model.Unit;

public interface AddUserView extends View {

    UiElement<String> getUsernameWidget();
    UiElement<String> getSurnameWidget();
    UiElement<String> getFirstNameWidget();
    UiElement<String> getRoleWidget();
    UiElement<String> getEmailWidget();
    UiElement<String> getPasswordWidget();
    UiElement<String> getSecondPasswordWidget();
    UiElement<String> getPermissionWidget();
    UiElement<Unit> getUnitWidget();

}
