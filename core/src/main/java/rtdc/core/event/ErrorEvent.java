package rtdc.core.event;

import rtdc.core.json.JSONObject;

import rtdc.core.model.ObjectType;
import rtdc.core.model.Property;
import rtdc.core.model.DataType;

public class ErrorEvent extends Event {

    public static final ObjectType<ErrorEvent> TYPE = ObjectType.build("error", ErrorEvent.class);
    public interface ErrorHandler extends EventHandler{ public void onError(ErrorEvent event);}

    public static final Property DESCRIPTION = new Property("description", DataType.STRING);

    public ErrorEvent(String description){
        this(new JSONObject("{}"));
        setProperty(DESCRIPTION, description);
    }

    public ErrorEvent(JSONObject jsonObject){
        super(TYPE, jsonObject, DESCRIPTION);
    }

    public String getDescription(){
        return (String) getProperty(DESCRIPTION);
    }

    public void fire(){
        for(ErrorHandler handler: getHandlers(TYPE))
            handler.onError(this);
    }

}
