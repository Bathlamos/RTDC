package rtdc.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import rtdc.android.MyActivity;
import rtdc.android.R;
import rtdc.core.controller.ActionListController;
import rtdc.core.impl.NumberAwareStringComparator;
import rtdc.core.model.Action;
import rtdc.core.view.ActionListView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionPlanActivity extends Activity implements ActionListView {

    List<Action> actions = new ArrayList<Action>();
    ListView actionListView;
    ArrayAdapter<Action> adapter;
    ArrayAdapter<Action> editAdapter;
    Boolean setEditable = false;
    private ActionListController controller;
    private HashMap<String, Integer> actionValues = new HashMap<String, Integer>();

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_plan);

        controller = new ActionListController(this);
        context = this.getBaseContext();
        actionListView = (ListView) findViewById(R.id.ActionListView);

        // Comment this out when connected to server ------
        addActions(5);
        adapter = new ActionListAdapter();
        editAdapter = new EditActionListAdapter();
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem save = menu.findItem(R.id.saveActionPlan);
        MenuItem discard = menu.findItem(R.id.discardActionPlan);
        MenuItem edit = menu.findItem(R.id.editActionPlan);

        save.setVisible(setEditable);
        discard.setVisible(setEditable);
        edit.setVisible(!setEditable);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.editActionPlan:
                setEditable = true;
                ((AdapterView)actionListView).setAdapter(editAdapter);
                invalidateOptionsMenu();
                return true;
            case R.id.saveActionPlan:
                invalidateOptionsMenu();
                setEditable = false;
                ((AdapterView)actionListView).setAdapter(adapter);
                return true;
            case R.id.discardActionPlan:
                invalidateOptionsMenu();
                setEditable = false;
                ((AdapterView)actionListView).setAdapter(adapter);
                return true;
            case R.id.action_go_to_manage:
                Intent intent = new Intent(this, MyActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
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

            return view;
        }
    }

    private class EditActionListAdapter extends ArrayAdapter<Action> {
        public EditActionListAdapter(){
            super(ActionPlanActivity.this, R.layout.adapter_edit_action_plan, actions);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            boolean actionListViewIsNull = false;

            if(view == null){
                view = getLayoutInflater().inflate(R.layout.adapter_edit_action_plan, parent, false);
                actionListViewIsNull = true;
            }

            Action currentAction = actions.get(position);

            Spinner status = (Spinner) view.findViewById(R.id.editStatus);
            ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(context, R.array.action_status, android.R.layout.simple_spinner_item);
            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            status.setAdapter(statusAdapter);

            TextView role = (TextView) view.findViewById(R.id.editRole);
            role.setText(currentAction.getRole());

            Spinner action = (Spinner) view.findViewById(R.id.editAction);
            ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(context, R.array.actions, android.R.layout.simple_spinner_item);
            actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            action.setAdapter(actionAdapter);

            TextView target = (TextView) view.findViewById(R.id.editTarget);
            target.setText(currentAction.getTarget());

            TextView deadline = (TextView) view.findViewById(R.id.editDeadline);
            deadline.setText(currentAction.getDeadline());

            EditText notes = (EditText) view.findViewById(R.id.editNotes);
            notes.setText(currentAction.getNotes());

            if(actionListViewIsNull) {
                notes.addTextChangedListener(new GenericTextWatcher(currentAction.getId(), "5"));
            }

            return view;
        }

        private class GenericTextWatcher implements TextWatcher {

            private int actionId;
            private String field;

            private GenericTextWatcher(int actionId, String field) {
                this.actionId = actionId;
                this.field = field;
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                // Save the value for the given tag
                try {
                    actionValues.put(actionId+":"+field, Integer.parseInt(value));
                } catch (NumberFormatException e) {

                }
            }
        }
    }

    private void addActions(int x) {
        for(int i = 0; i < x; i++) {
            Action sampleAction = new Action();
            sampleAction.setStatus("In Progress");
            sampleAction.setRole("Jennifer Joyce");
            sampleAction.setAction("Push for discharge");
            sampleAction.setTarget("John Peyton in D308");
            sampleAction.setDeadline("11:00 AM");
            sampleAction.setNotes("Aggressively push for all \"Potential Discharges\" to be actually discharged without pushing, we would discharge 3; with pushing, we'll discharge 4.");
            this.actions.add(sampleAction);
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