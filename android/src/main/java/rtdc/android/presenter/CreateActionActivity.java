package rtdc.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiDate;
import rtdc.android.impl.AndroidUiDropdownList;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddActionController;
import rtdc.core.impl.UiDropdownList;
import rtdc.core.impl.UiElement;
import rtdc.core.view.AddActionView;

import java.util.Date;

public class CreateActionActivity extends AbstractActivity implements AddActionView {

    private AddActionController controller;

    private AndroidUiString roleEdit, targetEdit, descriptionEdit;
    private AndroidUiDate deadlineEdit;
    private AndroidUiDropdownList unitSpinner, statusSpinner, taskSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);

        roleEdit = (AndroidUiString) findViewById(R.id.roleEdit);
        targetEdit = (AndroidUiString) findViewById(R.id.targetEdit);
        deadlineEdit = (AndroidUiDate) findViewById(R.id.deadlineEdit);
        descriptionEdit = (AndroidUiString) findViewById(R.id.descriptionEdit);

        unitSpinner = (AndroidUiDropdownList) findViewById(R.id.unitSpinner);
        statusSpinner = (AndroidUiDropdownList) findViewById(R.id.statusSpinner);
        taskSpinner = (AndroidUiDropdownList) findViewById(R.id.actionSpinner);

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
    public UiDropdownList<String> getStatusUiElement() {
        return statusSpinner;
    }

    @Override
    public UiElement<String> getRoleUiElement() {
        return roleEdit;
    }

    @Override
    public UiDropdownList<String> getTaskUiElement() {
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
}