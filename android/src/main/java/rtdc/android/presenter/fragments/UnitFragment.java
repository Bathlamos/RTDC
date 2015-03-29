package rtdc.android.presenter.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import rtdc.android.R;

import rtdc.core.Bootstrapper;
import rtdc.core.controller.CapacityOverviewController;
import rtdc.core.model.Unit;
import rtdc.core.util.Cache;
import rtdc.core.view.CapacityOverviewView;

import java.util.ArrayList;
import java.util.List;

public class UnitFragment extends AbstractFragment implements AbsListView.OnItemClickListener, CapacityOverviewView {

    private CapacityOverviewController controller;

    private List<Unit> units = new ArrayList<Unit>();
    private UnitListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unit_list, container, false);

        // Set the adapter
        AdapterView mListView = (AdapterView) view.findViewById(R.id.units_listView);
        mAdapter = new UnitListAdapter(getActivity(), units);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        if(controller != null)
            controller = new CapacityOverviewController(this);

        return view;
    }

    @Override
    public void setUnits(List<Unit> units) {
        this.units.clear();
        this.units.addAll(units);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Unit unit = (Unit) mAdapter.getItem(position);
        // TODO: Move that in controller
        Cache.getInstance().put("unit", unit);
        Bootstrapper.FACTORY.newDispatcher().goToUnitInfo(controller);
    }
}
