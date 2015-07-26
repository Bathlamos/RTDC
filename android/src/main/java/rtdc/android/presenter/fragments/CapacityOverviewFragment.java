package rtdc.android.presenter.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;
import rtdc.android.R;
import rtdc.core.controller.CapacityOverviewController;
import rtdc.core.model.Unit;
import rtdc.core.view.CapacityOverviewView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapacityOverviewFragment extends AbstractFragment implements CapacityOverviewView {

    private ArrayAdapter<Unit> adapter;
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private CapacityOverviewController controller;
    private TextView lastClicked = null;
    private boolean isAscending = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_capacity_overview, container, false);
        AdapterView unitListView = (AdapterView) view.findViewById(R.id.capacity_list_view);

        adapter = new UnitListAdapter(units, getActivity());
        unitListView.setAdapter(adapter);

        setupHeader(view, R.id.unitNameHeader);
        setupHeader(view, R.id.availableBedsHeader);
        setupHeader(view, R.id.potentialDCHeader);
        setupHeader(view, R.id.DCByDeadlineHeader);
        setupHeader(view, R.id.totalAdmitsHeader);
        setupHeader(view, R.id.admitsByDeadlineHeader);
        setupHeader(view, R.id.statusAtDeadlineHeader);
        ((TextView) view.findViewById(R.id.unitNameHeader)).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_white_24dp, 0);

        if (controller == null)
            controller = new CapacityOverviewController(this);

        return view;
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

            setupColumn(view, R.id.unitName,        currentUnit.getName());
            setupColumn(view, R.id.availableBeds,   Integer.toString(currentUnit.getAvailableBeds()));
            setupColumn(view, R.id.potentialDC,     Integer.toString(currentUnit.getPotentialDc()));
            setupColumn(view, R.id.DCByDeadline,    Integer.toString(currentUnit.getDcByDeadline()));
            setupColumn(view, R.id.totalAdmits,     Integer.toString(currentUnit.getTotalAdmits()));
            setupColumn(view, R.id.admitsByDeadline,Integer.toString(currentUnit.getAdmitsByDeadline()));
            setupColumn(view, R.id.statusAtDeadline,Integer.toString(status));

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

        private void setupColumn(View view, int resourceId, String text){
            TextView textView = (TextView) view.findViewById(resourceId);
            textView.setText(text);
        }
    }

    private void setupHeader(View view, int resourceId){
        TextView textView = (TextView) view.findViewById(resourceId);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView clicked = (TextView) v;

                if (lastClicked != null && clicked != lastClicked)
                    lastClicked.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0); // Clear all drawables

                if (lastClicked == clicked)
                    isAscending = !isAscending;
                else
                    isAscending = true;

                lastClicked = clicked;

                if (isAscending)
                    clicked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_up_white_24dp, 0);
                else
                    clicked.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down_white_24dp, 0);

                switch (v.getId()) {
                    case R.id.unitNameHeader:
                        controller.sortUnits(Unit.Properties.name, isAscending);
                        break;
                    case R.id.availableBedsHeader:
                        controller.sortUnits(Unit.Properties.availableBeds, isAscending);
                        break;
                    case R.id.potentialDCHeader:
                        controller.sortUnits(Unit.Properties.potentialDc, isAscending);
                        break;
                    case R.id.DCByDeadlineHeader:
                        controller.sortUnits(Unit.Properties.dcByDeadline, isAscending);
                        break;
                    case R.id.totalAdmitsHeader:
                        controller.sortUnits(Unit.Properties.totalAdmits, isAscending);
                        break;
                    case R.id.admitsByDeadlineHeader:
                        controller.sortUnits(Unit.Properties.admitsByDeadline, isAscending);
                        break;
                    case R.id.statusAtDeadlineHeader:
                        controller.sortUnits(Unit.Properties.statusAtDeadline, isAscending);
                        break;
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}