package rtdc.android.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import rtdc.android.R;
import rtdc.core.controller.AddUnitController;
import rtdc.core.view.AddUnitView;

public class CreateUnitActivity extends Activity implements AddUnitView{

    private AddUnitController controller;

    private EditText unitNameEdit, totalBedsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);

        controller = new AddUnitController(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_unit, menu);

        unitNameEdit = (EditText) findViewById(R.id.unitNameEdit);
        totalBedsEdit = (EditText) findViewById(R.id.totalBedsEdit);

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
