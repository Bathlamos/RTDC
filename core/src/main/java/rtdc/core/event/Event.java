package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import rtdc.core.json.JSONObject;
import rtdc.core.model.RtdcObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import rtdc.core.model.Property;
import static rtdc.core.model.Property.DataType.*;
import static rtdc.core.model.Property.ValidationConstraint.*;

public abstract class Event<T extends EventHandler> extends RtdcObject {

    static Function<JSONObject, Event> initializer = new Function<JSONObject, Event>(){

        @Override
        public Event apply(JSONObject object) {
            EventType<EventHandler> type = EventType.build(object.optString("name"));
            if(ErrorEvent.TYPE.equals(type))
                return new ErrorEvent(object);
            if(AuthenticationEvent.TYPE.equals(type))
                return new AuthenticationEvent(object);
            if(UpdateCompleteEvent.UNIT_UPDATED.equals(type))
                return new UpdateCompleteEvent(object);
            if(FetchUnitsEvent.TYPE.equals(type))
                return new FetchUnitsEvent(object);
            return null;
        }
    };

    private static Map<EventType, EventAggregator> handlers = new HashMap<EventType, EventAggregator>();
    public static final Property NAME = new Property("name", STRING, NOT_EMPTY);

    protected Event(EventType<T> type, JSONObject jsonObject, Property... properties){
        super(init(properties), jsonObject);
        setProperty(NAME, type.toString());
    }

    private static Set<Property> init(Property... properties){
        Set<Property> props = new HashSet<Property>();
        props.add(NAME);
        if(properties != null){
            for(Property p: properties)
                props.add(p);
        }
        return props;
    }

    public static void fire(JSONObject object){
        Event event = initializer.apply(object);
        if(event == null)
            new ErrorEvent("Message type not recognized " + object.toString()).fire();
        else{
            Multimap<Property, String> violations = event.getConstraintsViolations();
            if(!violations.isEmpty())
                new ErrorEvent("Constrain violation when translating " + object.toString()).fire();
            else
                event.fire();
            return;
        }
    }

    public static <T extends EventHandler> void subscribe(EventType<T> eventType, T eventHandler){
        if(!handlers.containsKey(eventType))
            handlers.put(eventType, new EventAggregator<T>());
        handlers.get(eventType).addHandler(eventHandler);
    }

    protected ImmutableSet<T> getHandlers(EventType<T> type){
        return handlers.get(type).getHandlers();
    }

    abstract void fire();

}
