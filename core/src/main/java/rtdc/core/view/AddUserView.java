package rtdc.core.view;

import rtdc.core.impl.UiDropdown;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Unit;
import rtdc.core.model.User;

public interface AddUserView extends Dialog {

    UiElement<String> getUsernameUiElement();

    UiElement<String> getLastNameUiElement();

    UiElement<String> getFirstNameUiElement();

    UiElement<String> getEmailUiElement();

    UiElement<String> getPhoneUiElement();

    UiDropdown<User.Role> getRoleUiElement();

    UiDropdown<User.Permission> getPermissionUiElement();

    UiElement<String> getPasswordUiElement();

    UiElement<String> getConfirmPasswordUiElement();

    void hideDeleteButton();
}
