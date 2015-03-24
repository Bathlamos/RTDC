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
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.view.AddActionView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CreateActionActivity extends Activity implements AddActionView {

    // private AddActionController controller;

    private EditText roleEdit, targetEdit, deadlineEdit, notesEdit;
    private Spinner unitSpinner, statusSpinner, actionSpinner;
    List<String> unitArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);

        roleEdit = (EditText) findViewById(R.id.roleEdit);
        targetEdit = (EditText) findViewById(R.id.targetEdit);
        deadlineEdit = (EditText) findViewById(R.id.deadlineEdit);
        notesEdit = (EditText) findViewById(R.id.notesEdit);

        unitArray =  new ArrayList<String>();
        unitArray.add("1E");
        unitArray.add("2E");

        unitSpinner = (Spinner) findViewById(R.id.unitSpinner);
        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, unitArray);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(unitAdapter);

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<CharSequence> satusAdapter = ArrayAdapter.createFromResource(this, R.array.action_status, android.R.layout.simple_spinner_item);
        satusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(satusAdapter);

        actionSpinner = (Spinner) findViewById(R.id.actionSpinner);
        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(this, R.array.actions, android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(actionAdapter);

        Intent intent = getIntent();
        String actionId = intent.getStringExtra("actionId");

        if (actionId != null) {
            Action action = new Action(new JSONObject(actionId));
            Unit unit = action.getUnit();
            setUnitAsString(unit.getName());
            setStatusAsString(action.getStatus());
            setRoleAsString(action.getRoleResponsible());
            setActionAsString(action.getTask());
            setTargetAsString(action.getTarget());
            setDeadlineAsString(action.getDeadline().toString());
            //setNotesAsString(action.getNotes());
        }

        // controller = new AddActionController(this);

        deadlineEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    showDeadlineTimePicker();
                }
            }
        });
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
                // TODO - Save changes to database
                // controller.addAction();
                return true;
            case R.id.action_cancel_action:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onDeadlineClick(View v) {
        showDeadlineTimePicker();
    }

    public void showDeadlineTimePicker() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(CreateActionActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                deadlineEdit.setText(selectedHour + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);
        timePicker.setTitle("Select Time");
        timePicker.show();
    }

    // Unit methods
    @Override
    public String getUnitAsString() {
        return unitSpinner.getSelectedItem().toString();
    }

    @Override
    public void setUnitAsString(String value) {
        for(int i = 0; i < unitArray.size(); i++){
            if(unitArray.get(i).equals(value))
                unitSpinner.setSelection(i);
        }
    }

    @Override
    public void setErrorForUnit(String error) {}

    // Status methods
    @Override
    public String getStatusAsString() { return statusSpinner.getSelectedItem().toString(); }

    @Override
    public void setStatusAsString(String value) {
        TypedArray ta = getResources().obtainTypedArray(R.array.action_status);
        for(int i = 0; i < ta.length(); i++){
            if(ta.getString(i).equals(value))
                statusSpinner.setSelection(i);
        }
    }

    @Override
    public void setErrorForStatus(String error) {}

    // Role methods
    @Override
    public String getRoleAsString() {return roleEdit.getText().toString(); }

    @Override
    public void setRoleAsString(String value) { roleEdit.setText(value); }

    @Override
    public void setErrorForRole(String error) {}

    // Action methods
    @Override
    public String getActionAsString() { return actionSpinner.getSelectedItem().toString(); }

    @Override
    public void setActionAsString(String value) {
        TypedArray ta = getResources().obtainTypedArray(R.array.actions);
        for(int i = 0; i < ta.length(); i++){
            if(ta.getString(i).equals(value))
                actionSpinner.setSelection(i);
        }
    }

    @Override
    public void setErrorForAction(String error) {}

    // Target methods
    @Override
    public String getTargetAsString() { return targetEdit.getText().toString(); }

    @Override
    public void setTargetAsString(String value) { targetEdit.setText(value); }

    @Override
    public void setErrorForTarget(String error) {}

    // Deadline methods
    @Override
    public String getDeadlineAsString() { return deadlineEdit.getText().toString(); }

    @Override
    public void setDeadlineAsString(String value) { deadlineEdit.setText(value); }

    @Override
    public void setErrorForDeadline(String error) {}

    // Notes methods
    @Override
    public String getNotesAsString() { return notesEdit.getText().toString(); }

    @Override
    public void setNotesAsString(String value) { notesEdit.setText(value); }

    @Override
    public void setErrorForNotes(String error) {}


    @Override
    public void displayPermanentError(String title, String error) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .show();
    }

    @Override
    public void displayError(String title, String error) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(error)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .show();
    }
}
