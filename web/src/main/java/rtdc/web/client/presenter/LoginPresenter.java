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