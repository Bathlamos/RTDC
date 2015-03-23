package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.User;

public class ErrorEvent extends Event<ErrorEvent.ErrorHandler> {

    public static final EventType<ErrorHandler> TYPE = new EventType<ErrorHandler>("errorEvent");

    public enum Properties{
        description
    }

    private final String description;

    public interface ErrorHandler extends EventHandler{ public void onError(ErrorEvent event);}


    public ErrorEvent(String description){
        this.description = description;
    }

    public ErrorEvent(JSONObject object){
        description = object.optString(Properties.description.name());
    }

    public String getDescription(){
        return description;
    }


    public void fire(){
        for(ErrorHandler handler: getHandlers(TYPE))
            handler.onError(this);
    }

    @Override
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.description.name(), description);
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }

}
