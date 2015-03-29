package rtdc.core.controller;

import rtdc.core.Bootstrapper;
import rtdc.core.event.Event;
import rtdc.core.event.FetchUnitsEvent;
import rtdc.core.impl.NumberAwareStringComparator;
import rtdc.core.model.SimpleComparator;
import rtdc.core.model.Unit;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;
import rtdc.core.view.CapacityOverviewView;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapacityOverviewController extends Controller<CapacityOverviewView> implements FetchUnitsEvent.Handler {

    private static final Logger logger = Logger.getLogger(CapacityOverviewController.class.getCanonicalName());

    private ArrayList<Unit> units;

    public CapacityOverviewController(CapacityOverviewView view){
        super(view);
        Event.subscribe(FetchUnitsEvent.TYPE, this);
        Service.getUnits();
    }

    @Override
    String getTitle() {
        return "Capacity Overview";
    }

    public void editCapacity(Unit unit){
        Cache.getInstance().put("unit", unit);
        Bootstrapper.FACTORY.newDispatcher().goToEditCapacity(this);
    }

    public void sortUnits(Unit.Properties property){
        logger.log(Level.INFO, "Sorting over " + property.name());
        ArrayList<Unit> sortedUnits = new ArrayList<>(units);
        if(property == Unit.Properties.id)
            Collections.sort(sortedUnits, new NumberAwareStringComparator());
        else
            Collections.sort(sortedUnits, SimpleComparator.forProperty(property));
        view.setUnits(sortedUnits);
    }

    @Override
    public void onUnitsFetched(FetchUnitsEvent event) {
        units = new ArrayList<>(event.getUnits());
        sortUnits(Unit.Properties.name);
    }

}
