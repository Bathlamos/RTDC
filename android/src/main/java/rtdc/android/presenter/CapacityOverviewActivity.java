package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import rtdc.android.AdminActivity;
import rtdc.android.R;
import rtdc.core.controller.CapacityOverviewController;
import rtdc.core.impl.NumberAwareStringComparator;
import rtdc.core.model.Unit;
import rtdc.core.util.Cache;
import rtdc.core.view.UnitListView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapacityOverviewActivity extends AbstractActivity implements UnitListView {

    List<Unit> units = new ArrayList<Unit>();
    ListView unitListView;
    ArrayAdapter<Unit> adapter;
    private Unit unitSelected;
    private CapacityOverviewController controller;

    Context context;
    private Intent editIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_overview);
        setTitle(R.string.title_activity_capacity_overview);

        context = this.getBaseContext();
        editIntent = new Intent(this, EditCapacityActivity.class);
        unitListView = (ListView) findViewById(R.id.CapacityListView);

        if(controller == null)
            controller = new CapacityOverviewController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_capacity_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_go_to_manage:
                intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_go_to_action_plan:
                intent = new Intent(this, ActionPlanActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onOptionsMenuClick(View v) {
        openContextMenu(v);
    }

    public void onHeaderItemClick(View v) {
        switch(v.getId()) {
            case R.id.unitNameHeader:
                Collections.sort(units, new NumberAwareStringComparator());
                break;
            case R.id.availableBedsHeader:
                units = controller.sortUnits(Unit.Properties.availableBeds);
                break;
            case R.id.potentialDCHeader:
                units = controller.sortUnits(Unit.Properties.potentialDc);
                break;
            case R.id.DCByDeadlineHeader:
                units = controller.sortUnits(Unit.Properties.dcByDeadline);
                break;
            case R.id.totalAdmitsHeader:
                units = controller.sortUnits(Unit.Properties.totalAdmits);
                break;
            case R.id.admitsByDeadlineHeader:
                units = controller.sortUnits(Unit.Properties.admitsByDeadline);
                break;
            case R.id.statusAtDeadlineHeader:
                units = controller.sortUnits(Unit.Properties.statusAtDeadline);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    private class UnitListAdapter extends ArrayAdapter<Unit> {
        public UnitListAdapter(){
            super(CapacityOverviewActivity.this, R.layout.adapter_capacity_overview, units);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if(view == null)
                view = getLayoutInflater().inflate(R.layout.adapter_capacity_overview, parent, false);

            Unit currentUnit = units.get(position);

            int status = currentUnit.getAvailableBeds() + currentUnit.getDcByDeadline() - currentUnit.getAdmitsByDeadline();

            TextView unitName = (TextView) view.findViewById(R.id.unitName);
            unitName.setText(currentUnit.getName());

            TextView availableBeds = (TextView) view.findViewById(R.id.availableBeds);
            availableBeds.setText(Integer.toString(currentUnit.getAvailableBeds()));

            TextView potentialDC = (TextView) view.findViewById(R.id.potentialDC);
            potentialDC.setText(Integer.toString(currentUnit.getPotentialDc()));

            TextView DCByDeadline = (TextView) view.findViewById(R.id.DCByDeadline);
            DCByDeadline.setText(Integer.toString(currentUnit.getDcByDeadline()));

            TextView totalAdmits = (TextView) view.findViewById(R.id.totalAdmits);
            totalAdmits.setText(Integer.toString(currentUnit.getTotalAdmits()));

            TextView admitsByDeadline = (TextView) view.findViewById(R.id.admitsByDeadline);
            admitsByDeadline.setText(Integer.toString(currentUnit.getAdmitsByDeadline()));

            TextView statusAtDeadline = (TextView) view.findViewById(R.id.statusAtDeadline);
            statusAtDeadline.setText(Integer.toString(status));

            ImageButton optionsMenuBtn = (ImageButton) view.findViewById(R.id.optionsMenuBtn);
            optionsMenuBtn.setTag(position);
            optionsMenuBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unitSelected = units.get(Integer.parseInt(v.getTag().toString()));
                    Cache.getInstance().put("unit", unitSelected);
                    startActivity(editIntent);
                }
            });

            return view;
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
}