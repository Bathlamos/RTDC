package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.Multimap;
import rtdc.core.json.JSONObject;
import rtdc.core.model.RtdcObject;

import java.util.HashSet;
import java.util.Set;

import static rtdc.core.model.RtdcObject.DataType.*;
import static rtdc.core.model.RtdcObject.ValidationConstraints.*;

public abstract class Event extends RtdcObject {

    static Function<JSONObject, Event> initializor = new Function<JSONObject, Event>(){

        @Override
        public Event apply(JSONObject object) {
            String type = object.optString("name");
            if(ErrorEvent.TYPE.equals(type))
                return new ErrorEvent(object);
            if(AuthenticationEvent.TYPE.equals(type))
                return new AuthenticationEvent(object);
            if(UpdateCompleteEvent.TYPE.equals(type))
                return new UpdateCompleteEvent(object);
            return null;
        }
    };

    protected static final Set handlers = new HashSet();

    public static final Property NAME = new Property("name", STRING, NOT_EMPTY);

    protected Event(String type, Set<Property> objectProperties, JSONObject jsonObject){
        super(init(objectProperties), jsonObject);
        setProperty(NAME, type);
    }

    private static Set<Property> init(Set<Property> objectProperties){
        objectProperties.add(NAME);
        return objectProperties;
    }

    public static void fire(JSONObject object){
        Event event = initializor.apply(object);
        if(event == null)
            new ErrorEvent("Message type not recognized " + object.toString()).fire();
        else{
            Multimap<Property, String> violations = event.getConstraintsViolations();
            if(!violations.isEmpty()) {
                new ErrorEvent("Constrain violation when translating " + object.toString()).fire();
            }else
                event.fire();
            return;
        }
    }

    abstract void fire();

}
