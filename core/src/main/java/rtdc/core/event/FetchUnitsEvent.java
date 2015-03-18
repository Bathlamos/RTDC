package rtdc.core.event;

import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Property;
import rtdc.core.model.Unit;

public class FetchUnitsEvent extends Event<FetchUnitsEvent.FetchUnitsHandler> {

    public static final EventType<FetchUnitsHandler> TYPE = EventType.build("fetchUnits");
    public interface FetchUnitsHandler extends EventHandler{ public void onUnitsFetched(FetchUnitsEvent event);}

    public static final Property UNITS = new Property("units", Property.DataType.USER);

    public FetchUnitsEvent(Unit[] units){
        this(new JSONObject("{}"));
        setProperty(UNITS, units);
    }

    public FetchUnitsEvent(JSONObject jsonObject){
        super(TYPE, jsonObject, UNITS);
    }

    @Override
    void fire() {
        for(FetchUnitsHandler handler: getHandlers(TYPE))
            handler.onUnitsFetched(this);
    }

    public Unit[] getUnits() {
        return getProperty(UNITS);
    }
}
