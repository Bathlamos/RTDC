/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import rtdc.core.impl.UiDropdown;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.view.AddUserView;
import rtdc.core.view.LoginView;
import rtdc.web.client.impl.GwtUiDropdown;
import rtdc.web.client.impl.GwtUiPasswordString;
import rtdc.web.client.impl.GwtUiString;

public class AddUserPresenter extends Composite implements AddUserView {

    interface LoginPresenterUiBinder extends UiBinder<FlowPanel, AddUserPresenter> {}
    private static LoginPresenterUiBinder ourUiBinder = GWT.create(LoginPresenterUiBinder.class);

    private final AddUserController controller;

    @UiField
    GwtUiString username, firstName, lastName, email, phone;
    @UiField
    GwtUiPasswordString password, confirmPassword;
    @UiField
    GwtUiDropdown role, permission, unit;
    @UiField
    Button addUser;

    public AddUserPresenter() {
        initWidget(ourUiBinder.createAndBindUi(this));

        addUser.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                controller.addUser(true);
            }
        });

        controller = new AddUserController(this);
    }

    @Override
    public void displayError(String title, String error) {
        Window.alert(title + " : " + error);
    }

    @Override
    public void clearError() {

    }

    @Override
    public UiElement<String> getUsernameUiElement() {
        return username;
    }

    @Override
    public UiElement<String> getLastNameUiElement() {
        return lastName;
    }

    @Override
    public UiElement<String> getFirstNameUiElement() {
        return firstName;
    }

    @Override
    public UiElement<String> getEmailUiElement() {
        return email;
    }

    @Override
    public UiElement<String> getPhoneUiElement() {
        return phone;
    }

    @Override
    public UiDropdown<Unit> getUnitUiElement() {
        return unit;
    }

    @Override
    public UiDropdown<User.Role> getRoleUiElement() {
        return role;
    }

    @Override
    public UiDropdown<User.Permission> getPermissionUiElement() {
        return permission;
    }

    @Override
    public UiElement<String> getPasswordUiElement() {
        return password;
    }

    @Override
    public UiElement<String> getConfirmPasswordUiElement() {
        return confirmPassword;
    }

    @Override
    public void hideDeleteButton() {}

    @Override
    public void closeDialog() {

    }
}