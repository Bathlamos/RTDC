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
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddUnitController;
import rtdc.core.exception.ValidationException;
import rtdc.core.i18n.ResBundle;
import rtdc.core.impl.UiElement;
import rtdc.core.model.SimpleValidator;
import rtdc.core.view.AddUnitView;

import java.util.Locale;

public class CreateUnitActivity extends AbstractDialog implements AddUnitView{

    private AddUnitController controller;

    private AndroidUiString unitNameEdit, totalBedsEdit;
    private boolean hideDeleteButton = false;
    private int initialTotalBedsValue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);
        setTitle(R.string.title_activity_create_unit);

        // Add the action bar at the top.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        unitNameEdit = (AndroidUiString) findViewById(R.id.unitNameEdit);
        unitNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateUnitNameUiElement();
            }
        });

        totalBedsEdit = (AndroidUiString) findViewById(R.id.totalBedsEdit);
        totalBedsEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateTotalBedsUiElement();
            }
        });

        if (controller == null)
            controller = new AddUnitController(this);

        if(!totalBedsEdit.getValue().isEmpty())
            initialTotalBedsValue = Integer.parseInt(totalBedsEdit.getValue());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_unit, menu);
        MenuItem deleteUnit = menu.findItem(R.id.action_discard_unit);
        if(hideDeleteButton) deleteUnit.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            //noinspection SimplifiableIfSta
            case R.id.action_save_new_unit:
                // Check to see if all fields are valid before proceeding
                controller.validateUnitNameUiElement();
                controller.validateTotalBedsUiElement();

                if(unitNameEdit.getError() != null || totalBedsEdit.getError() != null) {
                    new AlertDialog.Builder(this)
                            .setTitle(ResBundle.get().errorGeneral())
                            .setMessage(ResBundle.get().invalidFields())
                            .setNeutralButton(android.R.string.ok, null).show();
                    return true;
                }

                if (initialTotalBedsValue != -1 && Integer.parseInt(totalBedsEdit.getValue()) != initialTotalBedsValue) {
                    new AlertDialog.Builder(this)
                            .setTitle(ResBundle.get().confirm())
                            .setMessage(ResBundle.get().totalBedsWarning())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    controller.addUnit();
                                    Toast.makeText(CreateUnitActivity.this, ResBundle.get().unitModified(), Toast.LENGTH_SHORT).show();
                                }})
                            .setNegativeButton(android.R.string.no, null).show();
                    return true;
                }
                else {
                    // Only name has changed
                    controller.addUnit();
                }
                return true;
            case R.id.action_discard_unit:
                new AlertDialog.Builder(this)
                    .setTitle(ResBundle.get().confirm())
                    .setMessage(ResBundle.get().deleteUnitConfirmation())
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            controller.deleteUnit();
                            Toast.makeText(CreateUnitActivity.this, ResBundle.get().unitDeleted(), Toast.LENGTH_SHORT).show();
                        }})
                    .setNegativeButton(android.R.string.no, null).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public UiElement<String> getNameUiElement() {
        return unitNameEdit;
    }

    @Override
    public UiElement<String> getTotalBedsUiElement() {
        return totalBedsEdit;
    }

    @Override
    public void hideDeleteButton() {
        hideDeleteButton = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
