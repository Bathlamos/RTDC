package rtdc.core.controller;

import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.view.UnitListView;

import java.util.*;

public class CapacityOverviewController extends Controller<UnitListView> implements FetchUnitsEvent.Handler {

    private Set<Unit> units;

    public CapacityOverviewController(UnitListView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();
    }

    @Override
    String getTitle() {
        return "Capacity Overview";
    }

    public List<Unit> sortUnits(Unit.Properties property){
        LinkedList<Unit> sortedUnits = new LinkedList<>(units);
        Collections.sort(sortedUnits, SimpleComparator.forProperty(property));
        return sortedUnits;
    }

    public void deleteUnit(Unit unit){
        units.remove(unit);
        Service.deleteUnit(unit.getId());
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = new HashSet<>(event.getUnits());
        view.setUnits(sortUnits(Unit.Properties.name));
    }

}
