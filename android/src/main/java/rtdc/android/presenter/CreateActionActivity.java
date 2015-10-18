package rtdc.android.presenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiDate;
import rtdc.android.impl.AndroidUiDropdown;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddActionController;
import rtdc.core.impl.UiDropdown;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;
import rtdc.core.view.AddActionView;

import java.util.Date;

public class CreateActionActivity extends AbstractDialog implements AddActionView {

    private AddActionController controller;

    private AndroidUiString roleEdit, targetEdit, descriptionEdit;
    private AndroidUiDate deadlineEdit;
    private AndroidUiDropdown unitSpinner, statusSpinner, taskSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);

        // Add the action bar at the top.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roleEdit = (AndroidUiString) findViewById(R.id.roleEdit);
        targetEdit = (AndroidUiString) findViewById(R.id.targetEdit);
        deadlineEdit = (AndroidUiDate) findViewById(R.id.deadlineEdit);
        descriptionEdit = (AndroidUiString) findViewById(R.id.descriptionEdit);

        unitSpinner = (AndroidUiDropdown) findViewById(R.id.unitSpinner);
        statusSpinner = (AndroidUiDropdown) findViewById(R.id.statusSpinner);
        taskSpinner = (AndroidUiDropdown) findViewById(R.id.actionSpinner);

        if(controller == null)
            controller = new AddActionController(this);
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
        switch (item.getItemId()) {
            case R.id.action_save_action:
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
    public UiDropdown<Unit> getUnitUiElement() {
        return unitSpinner;
    }

    @Override
    public UiDropdown<Action.Status> getStatusUiElement() {
        return statusSpinner;
    }

    @Override
    public UiElement<String> getRoleUiElement() {
        return roleEdit;
    }

    @Override
    public UiDropdown<Action.Task> getTaskUiElement() {
        return taskSpinner;
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

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
