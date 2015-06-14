package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;

public class ErrorEvent extends Event<ErrorEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("errorEvent");

    public enum Properties implements ObjectProperty<ErrorEvent>{
        description
    }

    private final String description;

    public interface Handler extends EventHandler{ void onError(ErrorEvent event);}


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
        for(Handler handler: getHandlers(TYPE))
            handler.onError(this);
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case description: return description;
        }
        return null;
    }

}
