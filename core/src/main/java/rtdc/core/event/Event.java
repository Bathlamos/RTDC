package rtdc.core.event;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import rtdc.core.json.JSONObject;
import rtdc.core.model.JsonDeserializer;
import rtdc.core.model.ObjectType;
import rtdc.core.model.RtdcObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rtdc.core.model.Property;

public abstract class Event extends RtdcObject {

    private static Map<ObjectType, EventAggregator> handlers = new HashMap<ObjectType, EventAggregator>();

    protected Event(ObjectType type, JSONObject jsonObject, Property... properties){
        super(type, init(properties), jsonObject);
    }

    private static Set<Property> init(Property... properties){
        Set<Property> props = new HashSet<Property>();
        if(properties != null)
            for(Property p: properties)
                props.add(p);
        return props;
    }

    public static void fire(JSONObject object){
        RtdcObject rtdcObject = JsonDeserializer.execute(object);
        if(rtdcObject == null || !(rtdcObject instanceof Event))
            new ErrorEvent("Message type not recognized " + object.toString()).fire();
        else{
            Multimap<Property, String> violations = rtdcObject.getConstraintsViolations();
            if(!violations.isEmpty())
                new ErrorEvent("Constraint violation when translating " + object.toString()).fire();
            else
                ((Event) rtdcObject).fire();
            return;
        }
    }

    public static <T extends Event> void subscribe(ObjectType<T> eventType, T eventHandler){
        if(!handlers.containsKey(eventType))
            handlers.put(eventType, new EventAggregator<T>());
        handlers.get(eventType).addHandler(eventHandler);
    }

    protected <T> ImmutableSet<T> getHandlers(ObjectType type){
        return handlers.get(type).getHandlers();
    }

    abstract void fire();

}
