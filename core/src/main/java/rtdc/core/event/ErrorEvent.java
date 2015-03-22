package rtdc.core.event;

import rtdc.core.json.JSONObject;

import rtdc.core.model.Field;
import rtdc.core.model.DataType;
import rtdc.core.model.RtdcObject;

public class ErrorEvent extends Event<ErrorEvent.ErrorHandler> {

    public static final DataType<ErrorEvent> TYPE = DataType.extend(RtdcObject.TYPE, "errorEvent",
            ErrorEvent.class,
            new Field("description", DataType.STRING));

    public interface ErrorHandler extends EventHandler<ErrorEvent>{ public void onError(ErrorEvent event);}

    public ErrorEvent(String description){
        this(new JSONObject("{}"));
        setProperty("description", description);
    }

    public ErrorEvent(JSONObject jsonObject){
        super(jsonObject);
    }

    @Override
    public DataType getType() {
        return null;
    }

    public String getDescription(){
        return (String) getProperty("description");
    }

    public void fire(){
        for(ErrorHandler handler: getHandlers(TYPE))
            handler.onError(this);
    }

}
