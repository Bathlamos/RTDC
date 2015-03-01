package rtdc.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import rtdc.core.controller.UnitListController;
import rtdc.core.model.Unit;
import rtdc.core.view.UnitListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CapacityOverviewActivity extends Activity implements UnitListView{

    List<Unit> units = new ArrayList<Unit>();
    ListView unitListView;
    ArrayAdapter<Unit> adapter;
    Boolean setEditable = false;
    private UnitListController controller;
    private HashMap<String, Integer> capacityValues = new HashMap<String, Integer>();

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_overview);

        controller = new UnitListController(this);

        context = this.getBaseContext();

        unitListView = (ListView) findViewById(R.id.CapacityListView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capacity_overview, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem save = menu.findItem(R.id.saveCapacity);
        MenuItem discard = menu.findItem(R.id.discardCapacity);
        MenuItem edit = menu.findItem(R.id.editCapacity);

        save.setVisible(setEditable);
        discard.setVisible(setEditable);
        edit.setVisible(!setEditable);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.editCapacity:
                setEditable = true;
                adapter.notifyDataSetChanged();
                invalidateOptionsMenu();
                return true;
            case R.id.saveCapacity:
                // TODO - Commit changes to Database
                updateCapacity();
                invalidateOptionsMenu();
                setEditable = false;
                adapter.notifyDataSetChanged();
                return true;
            case R.id.discardCapacity:
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

    private class UnitListAdapter extends ArrayAdapter<Unit> {
        public UnitListAdapter(){
            super(CapacityOverviewActivity.this, R.layout.adapter_capacity_overview, units);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){
            boolean unitListViewIsNull = false;

            if(view == null){
                view = getLayoutInflater().inflate(R.layout.adapter_capacity_overview, parent, false);
                unitListViewIsNull = true;
            }

            Unit currentUnit = units.get(position);

            int status = currentUnit.getAvailableBeds() + currentUnit.getDcByDeadline() - currentUnit.getAdmitsByDeadline();

            TextView unitName = (TextView) view.findViewById(R.id.unitName);
            unitName.setText(currentUnit.getName());
            EditText availableBeds = (EditText) view.findViewById(R.id.availableBeds);
            availableBeds.setText(Integer.toString(currentUnit.getAvailableBeds()));
            availableBeds.setTag(position+":"+1);
            EditText potentialDC = (EditText) view.findViewById(R.id.potentialDC);
            potentialDC.setText(Integer.toString(currentUnit.getPotentialDc()));
            potentialDC.setTag(position+":"+2);
            EditText DCByDeadline = (EditText) view.findViewById(R.id.DCByDeadline);
            DCByDeadline.setText(Integer.toString(currentUnit.getDcByDeadline()));
            DCByDeadline.setTag(position+":"+3);
            EditText totalAdmits = (EditText) view.findViewById(R.id.totalAdmits);
            totalAdmits.setText(Integer.toString(currentUnit.getTotalAdmits()));
            totalAdmits.setTag(position+":"+4);
            EditText admitsByDeadline = (EditText) view.findViewById(R.id.admitsByDeadline);
            admitsByDeadline.setText(Integer.toString(currentUnit.getAdmitsByDeadline()));
            admitsByDeadline.setTag(position+":"+5);
            TextView statusAtDeadline = (TextView) view.findViewById(R.id.statusAtDeadline);
            statusAtDeadline.setText(Integer.toString(status));

            if(unitListViewIsNull) {
                availableBeds.addTextChangedListener(new GenericTextWatcher(availableBeds));
                potentialDC.addTextChangedListener(new GenericTextWatcher(potentialDC));
                DCByDeadline.addTextChangedListener(new GenericTextWatcher(DCByDeadline));
                totalAdmits.addTextChangedListener(new GenericTextWatcher(totalAdmits));
                admitsByDeadline.addTextChangedListener(new GenericTextWatcher(admitsByDeadline));
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
            private GenericTextWatcher(View view) {
                this.view = view;
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                // Save the value for the given tag
                try {
                    capacityValues.put(view.getTag().toString(), Integer.parseInt(value));
                } catch(NumberFormatException e) {

                }
            }
        }
    }

    @Override
    public void setUnits(List<Unit> units) {
        this.units = units;
        adapter = new UnitListAdapter();
        ((AdapterView)unitListView).setAdapter(adapter);
    }

    @Override
    public void displayPermanentError(String title, String error) {
        Toast.makeText(this, title + "\nPermanent error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayError(String title, String error) {
        Toast.makeText(this, title + "\nError: " + error, Toast.LENGTH_SHORT).show();
    }

    // Update unit capacities with new values
    private void updateCapacity() {
        /*
        View view;
        EditText etAvailableBeds, etPotentialDC, etDcByDeadline, etTotalAdmits, etAdmitsByDeadline;
        int availableBeds, potentialDC, dcByDeadline, totalAdmits, admitsByDeadline;
        for (int i = 0; i < unitListView.getCount(); i++) {
            view = unitListView.getAdapter().getView(i, null, null);
            etAvailableBeds = (EditText) view.findViewById(R.id.availableBeds);
            availableBeds = Integer.parseInt(etAvailableBeds.getText().toString());
            etPotentialDC = (EditText) view.findViewById(R.id.potentialDC);
            potentialDC = Integer.parseInt(etPotentialDC.getText().toString());
            etDcByDeadline = (EditText) view.findViewById(R.id.DCByDeadline);
            dcByDeadline = Integer.parseInt(etDcByDeadline.getText().toString());
            etTotalAdmits = (EditText) view.findViewById(R.id.totalAdmits);
            totalAdmits = Integer.parseInt(etTotalAdmits.getText().toString());
            etAdmitsByDeadline = (EditText) view.findViewById(R.id.admitsByDeadline);
            admitsByDeadline = Integer.parseInt(etAdmitsByDeadline.getText().toString());
            if(availableBeds != units.get(i).getAvailableBeds()) units.get(i).setAvailableBeds(availableBeds);
            if(potentialDC != units.get(i).getPotentialDc()) units.get(i).setPotentialDc(potentialDC);
            if(dcByDeadline != units.get(i).getDcByDeadline()) units.get(i).setDcByDeadline(dcByDeadline);
            if(totalAdmits != units.get(i).getTotalAdmits()) units.get(i).setTotalAdmits(totalAdmits);
            if(admitsByDeadline != units.get(i).getAdmitsByDeadline()) units.get(i).setAdmitsByDeadline(admitsByDeadline);
            Toast.makeText(this, units.get(i).getAvailableBeds() + " changed for " + availableBeds, Toast.LENGTH_SHORT).show();
        }*/
        String[] tag;
        Unit unit;
        int value;

        for (HashMap.Entry<String, Integer> capacity : capacityValues.entrySet()) {
            tag = capacity.getKey().split(":");
            unit = units.get(Integer.parseInt(tag[0]));
            value = capacity.getValue();

            switch (Integer.parseInt(tag[1])) {
                case 1:
                    if(value != unit.getAvailableBeds()) unit.setAvailableBeds(value);
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
    }
}
