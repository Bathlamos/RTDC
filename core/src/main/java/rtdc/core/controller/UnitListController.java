package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.util.Pair;
import rtdc.core.view.UnitListView;

import java.util.*;

public class UnitListController extends Controller<UnitListView> implements FetchUnitsEvent.Handler {

    private ArrayList<Unit> units;

    public UnitListController(UnitListView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();
    }

    @Override
    String getTitle() {
        return "Units";
    }

    public void sortUnits(Unit.Properties property, boolean ascending){
        ArrayList<Unit> sortedUnits = new ArrayList<>(units);
        Collections.sort(sortedUnits, SimpleComparator.forProperty(property).setAscending(ascending).build());
        view.setUnits(sortedUnits);
    }

    public void editUnit(Unit unit){
        Cache.getInstance().put("unit", unit);
        Bootstrapper.FACTORY.newDispatcher().goToEditUnit(this);
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = new ArrayList<>(event.getUnits());
        sortUnits(Unit.Properties.name, true);
    }

    // Update edited unit when returning from CreateUnitActivity
    public void updateUnits(){
        Pair<String, Unit> pair = (Pair<String, Unit>) Cache.getInstance().remove("unit");
        if(pair != null) {
            String action = pair.getFirst();
            Unit unit = pair.getSecond();
            if(action.equals("add")) {
                units.add(unit);
                sortUnits(Unit.Properties.name, true);
            } else {
                int unitID = unit.getId();
                int unitCount = units.size();
                for (int i = 0; i < unitCount; i++) {
                    if (units.get(i).getId() == unitID) {
                        if(action.equals("edit")) {
                            units.set(i, unit);
                            sortUnits(Unit.Properties.name, true);
                        } else {
                            units.remove(i);
                            view.setUnits(units);
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchUnitsEvent.TYPE, this);
    }
}
