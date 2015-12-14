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

package rtdc.android.presenter;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiDate;
import rtdc.android.impl.AndroidUiDropdown;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddActionController;
import rtdc.core.i18n.ResBundle;
import rtdc.core.impl.UiDropdown;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.view.AddActionView;

import java.util.Date;
import java.util.Locale;

public class CreateActionActivity extends AbstractDialog implements AddActionView {

    private AddActionController controller;

    private AndroidUiString roleEdit, targetEdit, descriptionEdit;
    private AndroidUiDate deadlineEdit;
    private AndroidUiDropdown unitSpinner, statusSpinner, taskSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);

        // Add the action bar at the top.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roleEdit = (AndroidUiString) findViewById(R.id.roleEdit);
        targetEdit = (AndroidUiString) findViewById(R.id.targetEdit);
        deadlineEdit = (AndroidUiDate) findViewById(R.id.deadlineEdit);
        descriptionEdit = (AndroidUiString) findViewById(R.id.descriptionEdit);
        descriptionEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateDescription();
            }
        });

        unitSpinner = (AndroidUiDropdown) findViewById(R.id.unitSpinner);
        statusSpinner = (AndroidUiDropdown) findViewById(R.id.statusSpinner);
        taskSpinner = (AndroidUiDropdown) findViewById(R.id.actionSpinner);

        if(controller == null)
            controller = new AddActionController(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        switch (item.getItemId()) {
            case R.id.action_save_action:
                controller.validateDescription();

                if(descriptionEdit.getError() != null){
                    new AlertDialog.Builder(this)
                            .setTitle(ResBundle.get().errorGeneral())
                            .setMessage(ResBundle.get().invalidFields())
                            .setNeutralButton(android.R.string.ok, null).show();
                    return true;
                }

                controller.addAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public UiDropdown<Unit> getUnitUiElement() {
        return unitSpinner;
    }

    @Override
    public UiDropdown<Action.Status> getStatusUiElement() {
        return statusSpinner;
    }

    @Override
    public UiElement<String> getRoleUiElement() {
        return roleEdit;
    }

    @Override
    public UiDropdown<Action.Task> getTaskUiElement() {
        return taskSpinner;
    }

    @Override
    public UiElement<String> getTargetUiElement() {
        return targetEdit;
    }

    @Override
    public UiElement<Date> getDeadlineUiElement() {
        return deadlineEdit;
    }

    @Override
    public UiElement<String> getDescriptionUiElement() {
        return descriptionEdit;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
