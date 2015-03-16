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

public class AddUserPresenter extends Composite {

    interface LoginPresenterUiBinder extends UiBinder<FlowPanel, AddUserPresenter> {}
    private static LoginPresenterUiBinder ourUiBinder = GWT.create(LoginPresenterUiBinder.class);

    @UiField
    TextBox username, firstname, surname, permission, email, role;
    @UiField
    PasswordTextBox password;
    @UiField
    Button addUser;

    public AddUserPresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));
    }
}