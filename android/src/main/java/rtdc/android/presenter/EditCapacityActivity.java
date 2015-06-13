package rtdc.android.presenter;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.EditCapacityController;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Unit;
import rtdc.core.util.Cache;
import rtdc.core.view.EditCapacityView;

public class EditCapacityActivity extends AbstractActivity implements EditCapacityView {

    private EditCapacityController controller;
    private AndroidUiString availableBedsEdit, potentialDcEdit, DcByDeadlineEdit, totalAdmitsEdit, admitsByDeadlineEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_capacity);

        availableBedsEdit = (AndroidUiString) findViewById(R.id.availableBedsEdit);
        potentialDcEdit = (AndroidUiString) findViewById(R.id.potentialDCEdit);
        DcByDeadlineEdit = (AndroidUiString) findViewById(R.id.DCByDeadlineEdit);
        totalAdmitsEdit = (AndroidUiString) findViewById(R.id.totalAdmitsEdit);
        admitsByDeadlineEdit = (AndroidUiString) findViewById(R.id.admitsByDeadlineEdit);

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
                controller.updateCapacity();
                return true;
            case R.id.action_cancel_capacity:
                finish();
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
