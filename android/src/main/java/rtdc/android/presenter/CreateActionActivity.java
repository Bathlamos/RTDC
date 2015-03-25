package rtdc.android.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiDate;
import rtdc.android.impl.AndroidUiDropdownList;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddActionController;
import rtdc.core.impl.UiDropdownList;
import rtdc.core.impl.UiElement;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.view.AddActionView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateActionActivity extends AbstractActivity implements AddActionView {

    private AddActionController controller;

    private AndroidUiString roleEdit, targetEdit, descriptionEdit;
    private AndroidUiDate deadlineEdit;
    private AndroidUiDropdownList unitSpinner, statusSpinner, actionSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);

        controller = new AddActionController(this);

        roleEdit = (AndroidUiString) findViewById(R.id.roleEdit);
        targetEdit = (AndroidUiString) findViewById(R.id.targetEdit);
        deadlineEdit = (AndroidUiDate) findViewById(R.id.deadlineEdit);
        descriptionEdit = (AndroidUiString) findViewById(R.id.descriptionEdit);

        unitSpinner = (AndroidUiDropdownList) findViewById(R.id.unitSpinner);

        statusSpinner = (AndroidUiDropdownList) findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> satusAdapter = ArrayAdapter.createFromResource(this, R.array.action_status, android.R.layout.simple_spinner_item);
        satusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(satusAdapter);

        actionSpinner = (AndroidUiDropdownList) findViewById(R.id.actionSpinner);
        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(this, R.array.actions, android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(actionAdapter);
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
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_save_action:
                intent = new Intent(this, ActionPlanActivity.class);
                startActivity(intent);
                controller.addAction();
                return true;
            case R.id.action_cancel_action:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public UiDropdownList<String> getUnitUiElement() {
        return unitSpinner;
    }

    @Override
    public UiElement<String> getStatusUiElement() {
        return statusSpinner;
    }

    @Override
    public UiElement<String> getRoleUiElement() {
        return roleEdit;
    }

    @Override
    public UiElement<String> getActionUiElement() {
        return actionSpinner;
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
}
