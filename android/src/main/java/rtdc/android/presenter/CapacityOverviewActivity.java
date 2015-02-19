package rtdc.android.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import rtdc.android.MyActivity;
import rtdc.android.R;
import rtdc.core.model.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CapacityOverviewActivity extends Activity{

    List<Unit> units = new ArrayList<Unit>();
    ListView unitListView;
    ArrayAdapter<Unit> adapter;
    Boolean setEditable = false;

    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_overview);

        context = this.getBaseContext();

        unitListView = (ListView) findViewById(R.id.CapacityListView);


        addUnits(5);

        adapter = new UnitListAdapter();
        unitListView.setAdapter(adapter);

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
            if(view == null){
                view = getLayoutInflater().inflate(R.layout.adapter_capacity_overview, parent, false);
            }

            Unit currentUnit = units.get(position);

            int status = currentUnit.getAvailableBeds() + currentUnit.getDcByDeadline() - currentUnit.getAdmitsByDeadline();

            TextView unitName = (TextView) view.findViewById(R.id.unitName);
            unitName.setText(currentUnit.getName());
            EditText availableBeds = (EditText) view.findViewById(R.id.availableBeds);
            availableBeds.setText(Integer.toString(currentUnit.getAvailableBeds()));
            EditText potentialDC = (EditText) view.findViewById(R.id.potentialDC);
            potentialDC.setText(Integer.toString(currentUnit.getPotentialDc()));
            EditText DCByDeadline = (EditText) view.findViewById(R.id.DCByDeadline);
            DCByDeadline.setText(Integer.toString(currentUnit.getDcByDeadline()));
            EditText totalAdmits = (EditText) view.findViewById(R.id.totalAdmits);
            totalAdmits.setText(Integer.toString(currentUnit.getTotalAdmits()));
            EditText admitsByDeadline = (EditText) view.findViewById(R.id.admitsByDeadline);
            admitsByDeadline.setText(Integer.toString(currentUnit.getAdmitsByDeadline()));
            TextView statusAtDeadline = (TextView) view.findViewById(R.id.statusAtDeadline);
            statusAtDeadline.setText(Integer.toString(status));

            if (setEditable && position == 0){
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
            } else if (position == 0){
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
    }

    private void addUnits(int num){
        Random rand = new Random();
        for(int i=0; i<num; i++){
            Unit unit = new Unit();
            unit.setName("12E");
            unit.setAvailableBeds(rand.nextInt((64) + 1));
            unit.setPotentialDc(rand.nextInt((64) + 1));
            unit.setDcByDeadline(rand.nextInt((64) + 1));
            unit.setTotalAdmits(rand.nextInt((64) + 1));
            unit.setAdmitsByDeadline(rand.nextInt((64) + 1));

            units.add(unit);
        }
    }
}
