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
import rtdc.core.view.LoginView;

public class LoginPresenter extends Composite implements LoginView {

    interface LoginPresenterUiBinder extends UiBinder<FlowPanel, LoginPresenter> {}
    private static LoginPresenterUiBinder ourUiBinder = GWT.create(LoginPresenterUiBinder.class);

    private LoginController controller = new LoginController(this);

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
    }

    @Override
    public String getUsername() {
        return username.getText();
    }

    @Override
    public void setUsername(String username) {
        this.username.setText(username);
    }

    @Override
    public String getPassword() {
        return password.getText();
    }

    @Override
    public void setPassword(String password) {
        this.username.setText(password);
    }

    @Override
    public void displayPermanentError(String title, String error) {
        Window.alert(title + " : " + error);
    }

    @Override
    public void displayError(String title, String error) {
        Window.alert(title + " : " + error);
    }
}