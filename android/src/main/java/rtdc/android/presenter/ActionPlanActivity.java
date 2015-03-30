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

public class ActionPlanActivity extends AbstractActivity implements ActionListView {

    private ActionListAdapter adapter;
    private ArrayList<Action> actions = new ArrayList<Action>();
    private Action actionSelected;
    private ActionListController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_plan);

        ListView actionListView = (ListView) findViewById(R.id.ActionListView);
        adapter = new ActionListAdapter(this, actions);
        actionListView.setAdapter(adapter);

        if(controller == null)
            controller = new ActionListController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                controller.editAction(actionSelected);
                break;
            case 2:
                controller.deleteAction(actionSelected);
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
                controller.sortActions(Action.Properties.status);
                break;
            case R.id.roleHeader:
                controller.sortActions(Action.Properties.roleResponsible);
                break;
            case R.id.actionHeader:
                controller.sortActions(Action.Properties.task);
                break;
            case R.id.targetHeader:
                controller.sortActions(Action.Properties.target);
                break;
            case R.id.deadlineHeader:
                controller.sortActions(Action.Properties.deadline);
                break;
        }
    }

    @Override
    public void setActions(List<Action> actions) {
        this.actions.clear();
        this.actions.addAll(actions);
        adapter.notifyDataSetChanged();
    }

    private static class ActionListAdapter extends ArrayAdapter<Action> {

        private Activity activity;

        public ActionListAdapter(Activity activity, List<Action> actions){
            super(activity, R.layout.adapter_action_plan, actions);
            this.activity = activity;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if(view == null)
                view = activity.getLayoutInflater().inflate(R.layout.adapter_action_plan, parent, false);

            Action currentAction = getItem(position);

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

            TextView description = (TextView) view.findViewById(R.id.description);
            description.setText(currentAction.getDescription());

            ImageButton optionsMenuBtn = (ImageButton) view.findViewById(R.id.optionsMenuBtn);
            optionsMenuBtn.setTag(position);
            activity.registerForContextMenu(optionsMenuBtn);

            return view;
        }
    }
}