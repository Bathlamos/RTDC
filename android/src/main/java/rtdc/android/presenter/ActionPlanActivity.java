package rtdc.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import rtdc.android.MyActivity;
import rtdc.android.R;
import rtdc.core.controller.ActionListController;
import rtdc.core.impl.NumberAwareStringComparator;
import rtdc.core.model.Action;
import rtdc.core.view.ActionListView;
import java.util.*;

public class ActionPlanActivity extends Activity implements ActionListView {

    List<Action> actions = new ArrayList<Action>();
    ListView actionListView;
    ArrayAdapter<Action> adapter;
    int editActionId;
    private ActionListController controller;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_plan);

        setTitle(getTitle()+" - 2E Unit");

        controller = new ActionListController(this);
        context = this.getBaseContext();
        actionListView = (ListView) findViewById(R.id.ActionListView);

        // Comment this out when connected to server ------
        addActions(5);
        adapter = new ActionListAdapter();
        ((AdapterView)actionListView).setAdapter(adapter);
        // ------------------------------------------------
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
                intent = new Intent(this, MyActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId( ) == R.id.optionsMenuBtn) {
            Action action = actions.get(Integer.parseInt(v.getTag().toString()));
            editActionId = action.getId();
            menu.setHeaderTitle(action.getAction()+": "+action.getTarget()+" "+editActionId);
            menu.add(0, 1, 0, "Edit");
            menu.add(0, 2, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                Intent intent = new Intent(this, CreateActionActivity.class);
                intent.putExtra("actionId", editActionId);
                startActivity(intent);
                break;
            case 2:
                // TODO - Delete from database
                deleteAction(editActionId);
                adapter.notifyDataSetChanged();
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
                Collections.sort(actions, Action.statusComparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.roleHeader:
                Collections.sort(actions, Action.roleComparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.actionHeader:
                Collections.sort(actions, Action.actionComparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.targetHeader:
                Collections.sort(actions, Action.targetComparator);
                adapter.notifyDataSetChanged();
                break;
            case R.id.deadlineHeader:
                Collections.sort(actions, Action.deadlineComparator);
                adapter.notifyDataSetChanged();
                break;
        }
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
            role.setText(currentAction.getRole());

            TextView action = (TextView) view.findViewById(R.id.action);
            action.setText(currentAction.getAction());

            TextView target = (TextView) view.findViewById(R.id.target);
            target.setText(currentAction.getTarget());

            TextView deadline = (TextView) view.findViewById(R.id.deadline);
            deadline.setText(currentAction.getDeadline());

            TextView notes = (TextView) view.findViewById(R.id.notes);
            notes.setText(currentAction.getNotes());

            ImageButton optionsMenuBtn = (ImageButton) view.findViewById(R.id.optionsMenuBtn);
            optionsMenuBtn.setTag(position);
            registerForContextMenu(optionsMenuBtn);

            return view;
        }
    }

    private void addActions(int x) {
        for(int i = 0; i < x; i++) {
            Action sampleAction = new Action();
            sampleAction.setId(i);
            sampleAction.setStatus("In Progress");
            sampleAction.setRole("Jennifer Joyce");
            sampleAction.setAction("Push for discharge");
            sampleAction.setTarget("John Peyton in D308");
            sampleAction.setDeadline("11:00 AM");
            sampleAction.setNotes("Aggressively push for all \"Potential Discharges\" to be actually discharged. Without pushing, we would discharge 3; with pushing, we'll discharge 4.");
            this.actions.add(sampleAction);
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