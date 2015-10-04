package rtdc.core.event;

import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.RootObject;

import java.util.HashMap;
import java.util.Map;

public abstract class Event<T extends EventHandler> extends RootObject {

    private static Map<EventType, EventAggregator> handlers = new HashMap<EventType, EventAggregator>();

    public static void fire(JSONObject object){
        String type = object.optString("_type");
        if(type == null)
            new ErrorEvent("Message type not recognized " + object.toString()).fire();
        else{
            Event e = null;
            if(type.equalsIgnoreCase(AuthenticationEvent.TYPE.getName()))
                e = new AuthenticationEvent(object);
            else if(type.equalsIgnoreCase(ErrorEvent.TYPE.getName()))
                e = new ErrorEvent(object);
            else if(type.equalsIgnoreCase(FetchUnitsEvent.TYPE.getName()))
                e = new FetchUnitsEvent(object);
            else if(type.equalsIgnoreCase(FetchUsersEvent.TYPE.getName()))
                e = new FetchUsersEvent(object);
            else if(type.equalsIgnoreCase(FetchUserEvent.TYPE.getName()))
                e = new FetchUserEvent(object);
            else if(type.equalsIgnoreCase(FetchActionsEvent.TYPE.getName()))
                e = new FetchActionsEvent(object);
            else if(type.equalsIgnoreCase(ActionCompleteEvent.TYPE.getName()))
                e = new ActionCompleteEvent(object);
            else if(type.equalsIgnoreCase(SessionExpiredEvent.TYPE.getName()))
                e = new SessionExpiredEvent();
            else if(type.equalsIgnoreCase(LogoutEvent.TYPE.getName()))
                e = new LogoutEvent();

            if( e != null)
                e.fire();
            else
                throw new RuntimeException("Event has not been registred under Event.java/fire: " + object.toString());
        }
    }

    public static <T extends EventHandler> void subscribe(EventType<T> eventType, T eventHandler){
        if(!handlers.containsKey(eventType))
            handlers.put(eventType, new EventAggregator<T>());
        handlers.get(eventType).addHandler(eventHandler);
    }

    public static <T extends EventHandler> void unsubscribe(EventType<T> eventType, T eventHandler){
        if(handlers.containsKey(eventType))
            handlers.get(eventType).removeHandler(eventHandler);
    }

    protected <T extends EventHandler> ImmutableSet<T> getHandlers(EventType<T> type){
        EventAggregator eventAggregator = handlers.get(type);
        if(eventAggregator == null)
            return ImmutableSet.of();
        else
            return eventAggregator.getHandlers();
    }

    abstract void fire();
}
