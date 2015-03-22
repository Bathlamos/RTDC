package rtdc.core.event;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import rtdc.core.json.JSONObject;
import rtdc.core.model.*;

import java.util.HashMap;
import java.util.Map;

public abstract class Event<T extends EventHandler> extends RtdcObject {

    private static Map<DataType, EventAggregator> handlers = new HashMap<DataType, EventAggregator>();

    protected Event(JSONObject jsonObject){
        super(jsonObject);
    }

    public static void fire(JSONObject object){
        RtdcObject rtdcObject = JsonDeserializer.instantiateObject(null, object);
        if(rtdcObject == null || !(rtdcObject instanceof Event))
            new ErrorEvent("Message type not recognized " + object.toString()).fire();
        else{
            Multimap<Field, String> violations = rtdcObject.validateObject();
            if(!violations.isEmpty())
                new ErrorEvent("Constraint violation when translating " + object.toString()).fire();
            else
                ((Event) rtdcObject).fire();
            return;
        }
    }

    public static <T extends Event> void subscribe(DataType<T> eventType, EventHandler<T> eventHandler){
        if(!handlers.containsKey(eventType))
            handlers.put(eventType, new EventAggregator<T>());
        handlers.get(eventType).addHandler(eventHandler);
    }

    protected <T extends EventHandler, O extends Event<T>> ImmutableSet<T> getHandlers(DataType<O> type){
        return handlers.get(type).getHandlers();
    }

    abstract void fire();

}
