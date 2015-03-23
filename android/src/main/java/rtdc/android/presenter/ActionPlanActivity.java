package rtdc.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
       // editAdapter = new EditActionListAdapter();
        ((AdapterView)actionListView).setAdapter(adapter);
        //((AdapterView)actionListView).setAdapter(editAdapter);
        // ------------------------------------------------

        /*
        TextView unitNameHeader = (TextView) findViewById(R.id.unitNameHeader);
        unitNameHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, new NumberAwareStringComparator());
                adapter.notifyDataSetChanged();
            }
        });

        TextView availableBedsHeader = (TextView) findViewById(R.id.availableBedsHeader);
        availableBedsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, Unit.availableBedsComparator);
                adapter.notifyDataSetChanged();
            }
        });

        TextView potentialDCHeader = (TextView) findViewById(R.id.potentialDCHeader);
        potentialDCHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, Unit.potentialDcComparator);
                adapter.notifyDataSetChanged();
            }
        });

        TextView DCByDeadlineHeader = (TextView) findViewById(R.id.DCByDeadlineHeader);
        DCByDeadlineHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, Unit.dcByDeadlineComparator);
                adapter.notifyDataSetChanged();
            }
        });

        TextView totalAdmitsHeader = (TextView) findViewById(R.id.totalAdmitsHeader);
        totalAdmitsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, Unit.totalAdmitsComparator);
                adapter.notifyDataSetChanged();
            }
        });

        TextView admitsByDeadlineHeader = (TextView) findViewById(R.id.admitsByDeadlineHeader);
        admitsByDeadlineHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, Unit.admitsByDeadlineComparator);
                adapter.notifyDataSetChanged();
            }
        });

        TextView statusAtDeadlineHeader = (TextView) findViewById(R.id.statusAtDeadlineHeader);
        statusAtDeadlineHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(units, Unit.statusAtDeadlineComparator);
                adapter.notifyDataSetChanged();
            }
        });*/
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
                editAdapter.notifyDataSetChanged();
                invalidateOptionsMenu();
                return true;
            case R.id.saveActionPlan:
                //updateActionPlan();
                invalidateOptionsMenu();
                setEditable = false;
                adapter.notifyDataSetChanged();
                return true;
            case R.id.discardActionPlan:
                invalidateOptionsMenu();
                setEditable = false;
                adapter.notifyDataSetChanged();
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

    /*
    private class EditActionListAdapter extends ArrayAdapter<Action> {
        public EditActionListAdapter(){
            super(ActionPlanActivity.this, R.layout.adapter_action_plan, actions);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            boolean actionListViewIsNull = false;

            if(view == null){
                view = getLayoutInflater().inflate(R.layout.adapter_action_plan, parent, false);
                actionListViewIsNull = true;
            }

            Action currentAction = actions.get(position);

            TextView status = (TextView) view.findViewById(R.id.status);
            status.setText(currentAction.getStatus());

            EditText availableBeds = (EditText) view.findViewById(R.id.availableBeds);
            availableBeds.setText(Integer.toString(currentUnit.getAvailableBeds()));
            availableBeds.setTag(currentUnit.getName()+":"+1);

            EditText potentialDC = (EditText) view.findViewById(R.id.potentialDC);
            potentialDC.setText(Integer.toString(currentUnit.getPotentialDc()));
            potentialDC.setTag(currentUnit.getName()+":"+2);

            EditText DCByDeadline = (EditText) view.findViewById(R.id.DCByDeadline);
            DCByDeadline.setText(Integer.toString(currentUnit.getDcByDeadline()));
            DCByDeadline.setTag(currentUnit.getName()+":"+3);

            EditText totalAdmits = (EditText) view.findViewById(R.id.totalAdmits);
            totalAdmits.setText(Integer.toString(currentUnit.getTotalAdmits()));
            totalAdmits.setTag(currentUnit.getName()+":"+4);

            EditText admitsByDeadline = (EditText) view.findViewById(R.id.admitsByDeadline);
            admitsByDeadline.setText(Integer.toString(currentUnit.getAdmitsByDeadline()));
            admitsByDeadline.setTag(currentUnit.getName()+":"+5);

            TextView statusAtDeadline = (TextView) view.findViewById(R.id.statusAtDeadline);
            statusAtDeadline.setText(Integer.toString(status));

            if(actionListViewIsNull) {
                availableBeds.addTextChangedListener(new GenericTextWatcher(currentUnit.getName(), "1"));
                potentialDC.addTextChangedListener(new GenericTextWatcher(currentUnit.getName(), "2"));
                DCByDeadline.addTextChangedListener(new GenericTextWatcher(currentUnit.getName(), "3"));
                totalAdmits.addTextChangedListener(new GenericTextWatcher(currentUnit.getName(), "4"));
                admitsByDeadline.addTextChangedListener(new GenericTextWatcher(currentUnit.getName(), "5"));
            }

            if(setEditable){
                Drawable originalBackground = new EditText(this.getContext()).getBackground();

                availableBeds.setEnabled(true);
                availableBeds.setBackground(originalBackground);
                potentialDC.setEnabled(true);
                potentialDC.setBackground(originalBackground);
                DCByDeadline.setEnabled(true);
                DCByDeadline.setBackground(originalBackground);
                totalAdmits.setEnabled(true);
                totalAdmits.setBackground(originalBackground);
                admitsByDeadline.setEnabled(true);
                admitsByDeadline.setBackground(originalBackground);
            } else {
                availableBeds.setEnabled(false);
                availableBeds.setBackgroundColor(Color.TRANSPARENT);
                potentialDC.setEnabled(false);
                potentialDC.setBackgroundColor(Color.TRANSPARENT);
                DCByDeadline.setEnabled(false);
                DCByDeadline.setBackgroundColor(Color.TRANSPARENT);
                totalAdmits.setEnabled(false);
                totalAdmits.setBackgroundColor(Color.TRANSPARENT);
                admitsByDeadline.setEnabled(false);
                admitsByDeadline.setBackgroundColor(Color.TRANSPARENT);
            }

            return view;
        }

        private class GenericTextWatcher implements TextWatcher {

            private View view;
            private String unit;
            private String field;

            private GenericTextWatcher(View view) {
                this.view = view;
            }
            private GenericTextWatcher(String unit, String field) {
                this.unit = unit;
                this.field = field;
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (setEditable) {
                    String value = editable.toString();
                    // Save the value for the given tag
                    try {
                        capacityValues.put(unit+":"+field, Integer.parseInt(value));
                    } catch (NumberFormatException e) {

                    }
                }
            }
        }
    }*/

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

    /*
    // Update unit capacities with new values
    private void updateActionPlan() {

        String[] tag;
        int value;

        for (HashMap.Entry<String, Integer> capacity : capacityValues.entrySet()) {
            tag = capacity.getKey().split(":");

            Unit unit = null;
            for (Unit u : units) {
                //if (u.getId() == Integer.parseInt(tag[0])) {
                Logger logger = Logger.getLogger("yolo");
                logger.log(Level.INFO, u.getName()+" == "+tag[0]+"?");
                if (u.getName().equals(tag[0])) {
                    unit = u;
                    logger.log(Level.INFO, "Yes!!");
                    break;
                }
            }

            value = capacity.getValue();

            switch (Integer.parseInt(tag[1])) {
                case 1:
                    if(value != unit.getAvailableBeds()) unit.setAvailableBeds(value);
                    Logger logger = Logger.getLogger("sdf");
                    logger.log(Level.INFO, "Changed column "+tag[1]+" for unit "+tag[0]+" with value "+value);
                    break;
                case 2:
                    if(value != unit.getPotentialDc()) unit.setPotentialDc(value);
                    break;
                case 3:
                    if(value != unit.getDcByDeadline()) unit.setDcByDeadline(value);
                    break;
                case 4:
                    if(value != unit.getTotalAdmits()) unit.setTotalAdmits(value);
                    break;
                case 5:
                    if(value != unit.getAdmitsByDeadline()) unit.setAdmitsByDeadline(value);
                    break;
            }
        }
        capacityValues.clear();
    }*/
}