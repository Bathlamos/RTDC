package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import rtdc.android.R;
import rtdc.core.controller.AddUnitController;
import rtdc.core.view.AddUnitView;

public class CreateUnitActivity extends AbstractActivity implements AddUnitView{

    private AddUnitController controller;

    private EditText unitNameEdit, totalBedsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_unit);
        setTitle(R.string.title_activity_create_unit);

        unitNameEdit = (EditText) findViewById(R.id.unitNameEdit);
        totalBedsEdit = (EditText) findViewById(R.id.totalBedsEdit);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (controller == null)
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
        switch (item.getItemId()) {
            //noinspection SimplifiableIfSta
            case R.id.action_save_new_unit:
                controller.addUnit();
                return true;
            case R.id.action_discard_unit:
                // TODO: Show confirmation dialog
                controller.deleteUnit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
