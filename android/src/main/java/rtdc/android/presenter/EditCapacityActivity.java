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

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.EditCapacityController;
import rtdc.core.i18n.MessageBundle;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Unit;
import rtdc.core.util.Cache;
import rtdc.core.view.EditCapacityView;

import java.util.Locale;

public class EditCapacityActivity extends AbstractDialog implements EditCapacityView {

    private EditCapacityController controller;
    private AndroidUiString availableBedsEdit, potentialDcEdit, DcByDeadlineEdit, totalAdmitsEdit, admitsByDeadlineEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_capacity);

        // Add the action bar at the top.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        availableBedsEdit = (AndroidUiString) findViewById(R.id.availableBedsEdit);
        availableBedsEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateAvailableBedsUiElement();
            }
        });

        potentialDcEdit = (AndroidUiString) findViewById(R.id.potentialDCEdit);
        potentialDcEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validatePotentialDcUiElement();
            }
        });

        DcByDeadlineEdit = (AndroidUiString) findViewById(R.id.DCByDeadlineEdit);
        DcByDeadlineEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateDcByDeadlineUiElement(true);
            }
        });

        totalAdmitsEdit = (AndroidUiString) findViewById(R.id.totalAdmitsEdit);
        totalAdmitsEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateTotalAdmitsUiElement();
            }
        });

        admitsByDeadlineEdit = (AndroidUiString) findViewById(R.id.admitsByDeadlineEdit);
        admitsByDeadlineEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateAdmitsByDeadlineUiElement(true);
            }
        });


        if(controller == null)
            controller = new EditCapacityController(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_capacity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case R.id.action_save_capacity:
                // Check to see if all fields are valid before proceeding
                controller.validateAvailableBedsUiElement();
                controller.validatePotentialDcUiElement();
                controller.validateDcByDeadlineUiElement(true);
                controller.validateTotalAdmitsUiElement();
                controller.validateAdmitsByDeadlineUiElement(true);

                if(availableBedsEdit.getError() != null || potentialDcEdit.getError() != null && DcByDeadlineEdit.getError() != null
                        || totalAdmitsEdit.getError() != null || admitsByDeadlineEdit.getError() != null) {
                    new AlertDialog.Builder(this)
                            .setTitle(MessageBundle.getBundle(Locale.ENGLISH).getString("errorGeneral"))
                            .setMessage(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidFields"))
                            .setNeutralButton(android.R.string.ok, null).show();
                    return true;
                }

                controller.updateCapacity();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public UiElement<String> getAvailableBedsUiElement() {
        return availableBedsEdit;
    }

    @Override
    public UiElement<String> getPotentialDcUiElement() {
        return potentialDcEdit;
    }

    @Override
    public UiElement<String> getDcByDeadlineUiElement() {
        return DcByDeadlineEdit;
    }

    @Override
    public UiElement<String> getTotalAdmitsUiElement() {
        return totalAdmitsEdit;
    }

    @Override
    public UiElement<String> getAdmitsByDeadlineUiElement() {
        return admitsByDeadlineEdit;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
