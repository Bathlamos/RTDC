package rtdc.web.client.impl;

import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import rtdc.core.impl.UiElement;

public class GwtUiPasswordString extends PasswordTextBox implements UiElement<String> {
    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }
}
