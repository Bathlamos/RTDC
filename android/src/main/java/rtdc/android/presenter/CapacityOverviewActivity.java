package rtdc.android.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.controller.CapacityOverviewController;
import rtdc.core.model.Unit;
import rtdc.core.view.CapacityOverviewView;

import java.util.*;

public class CapacityOverviewActivity extends AbstractActivity implements CapacityOverviewView {

    private ArrayAdapter<Unit> adapter;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private CapacityOverviewController controller;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity_overview);

        AdapterView unitListView = (AdapterView) findViewById(R.id.CapacityListView);

        adapter = new UnitListAdapter(units);
        unitListView.setAdapter(adapter);

        if (controller == null)
            controller = new CapacityOverviewController(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_capacity_overview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_go_to_manage:
                //TODO:Improve
                Bootstrapper.FACTORY.newDispatcher().goToAllUnits(controller); //adminActivity
                return true;
            case R.id.action_go_to_action_plan:
                Bootstrapper.FACTORY.newDispatcher().goToActionPlan(controller);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onOptionsMenuClick(View v) {
        openContextMenu(v);
    }

    public void onHeaderItemClick(View v) {
        switch (v.getId()) {
            case R.id.unitNameHeader:
                controller.sortUnits(Unit.Properties.id);
                break;
            case R.id.availableBedsHeader:
                controller.sortUnits(Unit.Properties.availableBeds);
                break;
            case R.id.potentialDCHeader:
                controller.sortUnits(Unit.Properties.potentialDc);
                break;
            case R.id.DCByDeadlineHeader:
                controller.sortUnits(Unit.Properties.dcByDeadline);
                break;
            case R.id.totalAdmitsHeader:
                controller.sortUnits(Unit.Properties.totalAdmits);
                break;
            case R.id.admitsByDeadlineHeader:
                controller.sortUnits(Unit.Properties.admitsByDeadline);
                break;
            case R.id.statusAtDeadlineHeader:
                controller.sortUnits(Unit.Properties.statusAtDeadline);
                break;
        }
    }

    @Override
    public void setUnits(List<Unit> units) {
        this.units.clear();
        this.units.addAll(units);
        adapter.notifyDataSetChanged();
    }

    private class UnitListAdapter extends ArrayAdapter<Unit> {

        public UnitListAdapter(List<Unit> units) {
            super(CapacityOverviewActivity.this, R.layout.adapter_capacity_overview, units);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
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
                    controller.editCapacity(units.get(Integer.parseInt(v.getTag().toString())));
                }

            });

            return view;
        }
    }
}
