package rtdc.web.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import rtdc.core.controller.AddUserController;
import rtdc.core.controller.LoginController;
import rtdc.core.view.AddUserView;
import rtdc.core.view.LoginView;

public class AddUserPresenter extends Composite implements AddUserView {

    interface LoginPresenterUiBinder extends UiBinder<FlowPanel, AddUserPresenter> {}
    private static LoginPresenterUiBinder ourUiBinder = GWT.create(LoginPresenterUiBinder.class);

    private final AddUserController controller;

    @UiField
    TextBox username, firstname, surname, permission, email, role,  phone;
    @UiField
    PasswordTextBox password;
    @UiField
    Button addUser;

    public AddUserPresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));

        addUser.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                controller.addUser();
            }
        });

        controller = new AddUserController(this);
    }

    @Override
    public String getUsernameAsString() {
        return username.getText();
    }

    @Override
    public void setUsernameAsString(String value) {
        username.setText(value);
    }

    @Override
    public void setErrorForUsername(String error) {
        username.setText(error);
    }

    @Override
    public String getSurnameAsString() {
        return surname.getText();
    }

    @Override
    public void setSurnameAsString(String value) {
        surname.setText(value);
    }

    @Override
    public void setErrorForSurname(String error) {
        surname.setText(error);
    }

    @Override
    public String getFirstnameAsString() {
        return firstname.getText();
    }

    @Override
    public void setFirstnameAsString(String value) {
        firstname.setText(value);
    }

    @Override
    public void setErrorForFirstname(String error) {
        firstname.setText(error);
    }

    @Override
    public String getEmailAsString() {
        return email.getText();
    }

    @Override
    public void setEmailAsString(String value) {
        email.setText(value);
    }

    @Override
    public long getPhoneAsLong() {
        return Long.valueOf(phone.getText());
    }

    @Override
    public void setPhoneAsLong(long value) {
        phone.setText(String.valueOf(value));
    }

    @Override
    public String getRoleAsString() {
        return role.getText();
    }

    @Override
    public void setRoleAsString(String value) {
        role.setText(value);
    }

    @Override
    public String getPasswordAsString() {
        return password.getText();
    }

    @Override
    public void setPasswordAsString(String value) {
        password.setText(value);
    }

    @Override
    public void setErrorForPassword(String error) {
        password.setText(error);
    }

    @Override
    public String getPermissionAsString() {
        return permission.getText();
    }

    @Override
    public void setPermissionAsString(String value) {
        permission.setText(value);
    }

    @Override
    public void setPermissionForSurname(String error) {
        permission.setText(error);
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