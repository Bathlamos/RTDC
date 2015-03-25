package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.*;

public class FetchUnitsEvent extends Event<FetchUnitsEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchUnitsEvent");

    public interface Handler extends EventHandler{ public void onUnitsFetched(FetchUnitsEvent event);}

    public enum Properties implements ObjectProperty<FetchUnitsEvent>{
        units
    }

    private final ImmutableSet<Unit> units;

    public FetchUnitsEvent(Iterable<Unit> units){
        this.units = ImmutableSet.copyOf(units);
    }

    public FetchUnitsEvent(JSONObject object){
        units = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.units.name()), new Function<JSONObject, Unit>() {
            @Override
            public Unit apply(JSONObject input) {
                return new Unit(input);
            }
        }));
    }

    public ImmutableSet<Unit> getUnits(){
        return units;
    }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onUnitsFetched(this);
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case units : return units;
        }
        return null;
    }
}
