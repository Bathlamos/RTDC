package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import rtdc.android.AdminActivity;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddUnitController;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Unit;
import rtdc.core.view.AddUnitView;

public class EditCapacityActivity extends AbstractActivity /*implements EditCapacityView*/{

    //private EditCapacityController controller;

    private AndroidUiString availableBedsEdit, potentialDCEdit, DCByDeadlineEdit, totalAdmitsEdit, admitsByDeadlineEdit;

    private Unit currentUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_capacity);

        availableBedsEdit = (AndroidUiString) findViewById(R.id.availableBedsEdit);
        potentialDCEdit = (AndroidUiString) findViewById(R.id.potentialDCEdit);
        DCByDeadlineEdit = (AndroidUiString) findViewById(R.id.DCByDeadlineEdit);
        totalAdmitsEdit = (AndroidUiString) findViewById(R.id.totalAdmitsEdit);
        admitsByDeadlineEdit = (AndroidUiString) findViewById(R.id.admitsByDeadlineEdit);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String unitJson = intent.getStringExtra("unitId");
        if (unitJson != null) {
            currentUnit = new Unit(new JSONObject(unitJson));
            setTitle("Edit Capacity - " + currentUnit.getName());
            availableBedsEdit.setText(currentUnit.getAvailableBeds());
            potentialDCEdit.setText(currentUnit.getPotentialDc());
            DCByDeadlineEdit.setText(currentUnit.getDcByDeadline());
            totalAdmitsEdit.setText(currentUnit.getTotalAdmits());
            admitsByDeadlineEdit.setText(currentUnit.getAdmitsByDeadline());
        }

        //if(controller == null)
         //   controller = new EditCapacityController(this);
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
                //controller.updateCapacity();
                Intent intent = new Intent(this, CapacityOverviewActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_discard_capacity:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
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
    }*/
}
