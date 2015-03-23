package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.*;

import java.util.Set;

public class FetchUnitsEvent extends Event<FetchUnitsEvent.FetchUnitsHandler> {

    public static final EventType<FetchUnitsHandler> TYPE = new EventType<FetchUnitsHandler>("fetchUnitsEvent");

    public interface FetchUnitsHandler extends EventHandler{ public void onUnitsFetched(FetchUnitsEvent event);}

    public enum Properties{
        users
    }

    private final ImmutableSet<Unit> units;

    public FetchUnitsEvent(Iterable<Unit> units){
        this.units = ImmutableSet.copyOf(units);
    }

    public FetchUnitsEvent(JSONObject object){
        units = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.users.name()), new Function<JSONObject, Unit>() {
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
        for(FetchUnitsHandler handler: getHandlers(TYPE))
            handler.onUnitsFetched(this);
    }

    @Override
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.users.name(), toJsonArray(units));
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }
}
