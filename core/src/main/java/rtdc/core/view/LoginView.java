package rtdc.core.view;

import rtdc.core.impl.UiElement;

public interface LoginView extends View {

    UiElement<String> getUsernameUiElement();
    UiElement<String> getPasswordUiElement();

}
