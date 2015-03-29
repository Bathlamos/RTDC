package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import rtdc.android.AdminActivity;
import rtdc.android.R;
import rtdc.core.controller.ActionListController;
import rtdc.core.model.Action;
import rtdc.core.util.Cache;
import rtdc.core.view.ActionListView;
import java.util.*;

public class ActionPlanActivity extends Activity implements ActionListView {

    private List<Action> actions = new ArrayList<Action>();
    private ListView actionListView;
    private ArrayAdapter<Action> adapter;
    private Action actionSelected;
    private ActionListController controller;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_plan);
        setTitle(getString(R.string.title_activity_action_plan) + " - Medicine Unit");

        if(controller == null)
            controller = new ActionListController(this);
        context = this.getBaseContext();
        actionListView = (ListView) findViewById(R.id.ActionListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.addAction:
                intent = new Intent(this, CreateActionActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_go_to_manage:
                intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_go_to_cap_overview:
                intent = new Intent(this, CapacityOverviewActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId( ) == R.id.optionsMenuBtn) {
            actionSelected = actions.get(Integer.parseInt(v.getTag().toString()));
            menu.setHeaderTitle(actionSelected.getTask() + ": " + actionSelected.getTarget());
            menu.add(0, 1, 0, "Edit");
            menu.add(0, 2, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                Cache.getInstance().put("action", actionSelected);
                Intent intent = new Intent(this, CreateActionActivity.class);
                startActivity(intent);
                break;
            case 2:
                controller.deleteAction(actionSelected);
                adapter.remove(actionSelected);
                Toast.makeText(this, "Action Deleted", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void onOptionsMenuClick(View v) {
        openContextMenu(v);
    }

    public void onHeaderItemClick(View v) {
        switch(v.getId()) {
            case R.id.statusHeader:
                actions = controller.sortActions(Action.Properties.status);
                break;
            case R.id.roleHeader:
                actions = controller.sortActions(Action.Properties.roleResponsible);
                break;
            case R.id.actionHeader:
                actions = controller.sortActions(Action.Properties.task);
                break;
            case R.id.targetHeader:
                actions = controller.sortActions(Action.Properties.target);
                break;
            case R.id.deadlineHeader:
                actions = controller.sortActions(Action.Properties.deadline);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private class ActionListAdapter extends ArrayAdapter<Action> {
        public ActionListAdapter(){
            super(ActionPlanActivity.this, R.layout.adapter_action_plan, actions);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if(view == null){
                view = getLayoutInflater().inflate(R.layout.adapter_action_plan, parent, false);
            }

            Action currentAction = actions.get(position);

            TextView status = (TextView) view.findViewById(R.id.status);
            status.setText(currentAction.getStatus());

            TextView role = (TextView) view.findViewById(R.id.role);
            role.setText(currentAction.getRoleResponsible());

            TextView action = (TextView) view.findViewById(R.id.action);
            action.setText(currentAction.getTask());

            TextView target = (TextView) view.findViewById(R.id.target);
            target.setText(currentAction.getTarget());

            TextView deadline = (TextView) view.findViewById(R.id.deadline);
            deadline.setText(currentAction.getDeadline().toString().substring(10, 16));

//            TextView notes = (TextView) view.findViewById(R.id.notes);
//            notes.setText(currentAction.getNotes());

            ImageButton optionsMenuBtn = (ImageButton) view.findViewById(R.id.optionsMenuBtn);
            optionsMenuBtn.setTag(position);
            registerForContextMenu(optionsMenuBtn);

            return view;
        }
    }

    private void deleteAction(int actionId) {
        for (int i = 0; i < actions.size(); i++) {
            if(actions.get(i).getId() == actionId) {
                actions.remove(i);
                break;
            }
        }
    }

    @Override
    public void setActions(List<Action> actions) {
        this.actions = actions;
        adapter = new ActionListAdapter();
        ((AdapterView)actionListView).setAdapter(adapter);
    }

    @Override
    public void displayPermanentError(String title, String error) {
        Toast.makeText(this, title + "\nPermanent error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayError(String title, String error) {
        Toast.makeText(this, title + "\nError: " + error, Toast.LENGTH_SHORT).show();
    }
}