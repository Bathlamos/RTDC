package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.event.FetchUsersEvent;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.UnitListView;
import rtdc.core.view.UserListView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public void sortUsers(Unit.Properties property){
        Collections.sort(units, SimpleComparator.forProperty(property));
        view.setUnits(units);
    }

    public void editUnit(Unit unit){
        Cache.getInstance().put("unit", unit);
        Bootstrapper.FACTORY.newDispatcher().goToEditUnit(this);
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = new ArrayList<>(event.getUnits());
        sortUsers(Unit.Properties.name);
    }

    @Override
    public void onStop() {
        super.onStop();
        Event.unsubscribe(FetchUnitsEvent.TYPE, this);
    }
}
