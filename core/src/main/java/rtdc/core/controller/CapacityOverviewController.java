package rtdc.core.controller;

import com.google.common.collect.ImmutableSet;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.Action;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.view.UnitListView;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class CapacityOverviewController extends Controller<UnitListView> implements FetchUnitsEvent.FetchUnitsHandler{

    private ImmutableSet<Unit> units;

    public CapacityOverviewController(UnitListView view){
        super(view);
        Service.getUnits();
    }

    public List<Unit> sortUnits(Unit.Properties property){
        LinkedList<Unit> sortedUnits = new LinkedList<>(units);
        Collections.sort(sortedUnits, SimpleComparator.forProperty(property));
        return sortedUnits;
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = event.getUnits();
        view.setUnits(sortUnits(Unit.Properties.name));
    }


}
