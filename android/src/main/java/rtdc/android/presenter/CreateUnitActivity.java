package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddUnitController;
import rtdc.core.impl.UiElement;
import rtdc.core.view.AddUnitView;

public class CreateUnitActivity extends AbstractDialog implements AddUnitView{

    private AddUnitController controller;

    private AndroidUiString unitNameEdit, totalBedsEdit;
    private boolean hideDeleteButton = false;

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
        totalBedsEdit = (AndroidUiString) findViewById(R.id.totalBedsEdit);

        if (controller == null)
            controller = new AddUnitController(this);
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
                controller.addUnit();
                return true;
            case R.id.action_discard_unit:
                new AlertDialog.Builder(this)
                    .setTitle("Confirm")
                    .setMessage("Delete current unit?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            controller.deleteUnit();
                            Toast.makeText(CreateUnitActivity.this, "Unit Deleted", Toast.LENGTH_SHORT).show();
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
