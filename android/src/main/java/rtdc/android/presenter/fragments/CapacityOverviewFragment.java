package rtdc.android.presenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import rtdc.android.R;
import rtdc.android.presenter.fragments.AbstractFragment;
import rtdc.core.controller.CapacityOverviewController;
import rtdc.core.model.Unit;
import rtdc.core.view.CapacityOverviewView;

import java.util.*;

public class CapacityOverviewFragment extends AbstractFragment implements CapacityOverviewView {

    private ArrayAdapter<Unit> adapter;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private CapacityOverviewController controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capacity_overview, container, false);
        AdapterView unitListView = (AdapterView) view.findViewById(R.id.CapacityListView);

        adapter = new UnitListAdapter(units, getActivity());
        unitListView.setAdapter(adapter);

        if (controller == null)
            controller = new CapacityOverviewController(this);

        return view;
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

        private LayoutInflater inflater;

        public UnitListAdapter(List<Unit> units, Context context) {
            super(context, R.layout.adapter_capacity_overview, units);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = inflater.inflate(R.layout.adapter_capacity_overview, parent, false);

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

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
