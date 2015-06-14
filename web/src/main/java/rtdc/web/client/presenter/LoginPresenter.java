package rtdc.web.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import rtdc.core.controller.LoginController;
import rtdc.core.impl.UiElement;
import rtdc.core.view.LoginView;

public class LoginPresenter extends Composite implements LoginView {

    interface LoginPresenterUiBinder extends UiBinder<FlowPanel, LoginPresenter> {}
    private static LoginPresenterUiBinder ourUiBinder = GWT.create(LoginPresenterUiBinder.class);

    private final LoginController controller;

    @UiField
    TextBox username;
    @UiField
    PasswordTextBox password;
    @UiField
    Button go;

    public LoginPresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));

        go.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                controller.login();
            }
        });

        controller = new LoginController(this);
    }

    @Override
    public void displayError(String title, String error) {
        Window.alert(title + " : " + error);
    }

    @Override
    public void clearError() {

    }

    @Override
    public UiElement<String> getPasswordUiElement() {
        return new UiElement<String>() {
            @Override
            public String getValue() {
                return password.getText();
            }

            @Override
            public void setValue(String value) {
                password.setText(value);
            }

            @Override
            public String getErrorMessage() {
                return null;
            }

            @Override
            public void setErrorMessage(String errorMessage) {

            }

            @Override
            public void setFocus(boolean hasFocus) {

            }
        };
    }

    @Override
    public UiElement<String> getUsernameUiElement() {
        return new UiElement<String>() {
            @Override
            public String getValue() {
                return username.getText();
            }

            @Override
            public void setValue(String value) {
                username.setText(value);
            }

            @Override
            public String getErrorMessage() {
                return null;
            }

            @Override
            public void setErrorMessage(String errorMessage) {

            }

            @Override
            public void setFocus(boolean hasFocus) {

            }
        };
    }
}