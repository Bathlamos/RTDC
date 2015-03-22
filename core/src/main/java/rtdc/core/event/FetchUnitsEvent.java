package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.DataType;
import rtdc.core.model.ObjectType;
import rtdc.core.model.Property;
import rtdc.core.model.Unit;

public class FetchUnitsEvent extends Event {

    public static final ObjectType<FetchUnitsEvent> TYPE = ObjectType.build("fetchUnitsEvent", FetchUnitsEvent.class);
    public interface FetchUnitsHandler extends EventHandler{ public void onUnitsFetched(FetchUnitsEvent event);}

    public static final Property UNITS = new Property("units", DataType.USER);

    public FetchUnitsEvent(Unit[] units){
        this(new JSONObject("{}"));
        setProperty(UNITS, units);
    }

    public FetchUnitsEvent(JSONObject jsonObject){
        super(TYPE, jsonObject, UNITS);
    }

    @Override
    void fire() {
        for(Object handler: getHandlers(TYPE))
            if(handler instanceof FetchUnitsHandler)
                ((FetchUnitsHandler) handler).onUnitsFetched(this);
    }

    public Unit[] getUnits() {
        return getProperty(UNITS);
    }
}
