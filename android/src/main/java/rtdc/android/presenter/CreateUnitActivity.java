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
import rtdc.core.controller.AddUnitController;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Unit;
import rtdc.core.view.AddUnitView;

public class CreateUnitActivity extends AbstractActivity implements AddUnitView{

    private AddUnitController controller;

    private EditText unitNameEdit, totalBedsEdit;

    private Unit currentUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);

        unitNameEdit = (EditText) findViewById(R.id.unitNameEdit);
        totalBedsEdit = (EditText) findViewById(R.id.totalBedsEdit);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String unitJson = intent.getStringExtra("unit");
        if (unitJson != null) {
            currentUnit = new Unit(new JSONObject(unitJson));
            unitNameEdit.setText(currentUnit.getName());
            totalBedsEdit.setText(Integer.toString(currentUnit.getTotalBeds()));
        }

        if(controller == null)
            controller = new AddUnitController(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_unit, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_new_unit) {
            controller.addUnit();
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_discard_unit) {
            if (currentUnit != null) {
                controller.deleteUnit(currentUnit);
                // Show confirmation dialog
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getNameAsString() {
        return unitNameEdit.getText().toString();
    }

    @Override
    public void setNameAsString(String name) {
        unitNameEdit.setText(name);
    }

    @Override
    public String getTotalBedsAsString() {
        return totalBedsEdit.getText().toString();
    }

    @Override
    public void setTotalBedsAsString(String totalBeds) {
        totalBedsEdit.setText(totalBeds);
    }

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
