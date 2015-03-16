package rtdc.core.event;

import rtdc.core.json.JSONObject;

import rtdc.core.model.Property;
import rtdc.core.model.Property.DataType;

public class ErrorEvent extends Event<ErrorEvent.ErrorHandler> {

    public static final EventType<ErrorHandler> TYPE = EventType.build("error");
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

    public void fire(ErrorHandler handler){
        handler.onError(this);
    }

}
