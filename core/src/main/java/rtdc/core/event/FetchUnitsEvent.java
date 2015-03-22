package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.*;

public class FetchUnitsEvent extends Event {

    public static final DataType<FetchUnitsEvent> TYPE = DataType.extend(RtdcObject.TYPE, "fetchUnitsEvent",
            FetchUnitsEvent.class,
            new Field("units", Array.TYPE, Unit.TYPE));

    public interface FetchUnitsHandler extends EventHandler<FetchUnitsEvent>{ public void onUnitsFetched(FetchUnitsEvent event);}

    public FetchUnitsEvent(Unit[] units){
        this(new JSONObject("{}"));
        setProperty("units", units);
    }

    public FetchUnitsEvent(JSONObject jsonObject){
        super(jsonObject);
    }

    @Override
    public DataType getType() {
        return TYPE;
    }

    @Override
    void fire() {
        for(Object handler: getHandlers(TYPE))
            if(handler instanceof FetchUnitsHandler)
                ((FetchUnitsHandler) handler).onUnitsFetched(this);
    }

    public Unit[] getUnits() {
        return (Unit[]) getProperty("units");
    }
}
