package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;

public class ActionCompleteEvent extends Event<ActionCompleteEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("actionCompleteEvent");

    public interface Handler extends EventHandler{ void onActionComplete(ActionCompleteEvent event);}

    public enum Properties implements ObjectProperty<ActionCompleteEvent>{
        objectId,
        objectType,
        action
    }

    private final String objectType;
    private final int objectId;
    private final String action;

    public ActionCompleteEvent(int objectId, String objectType, String action){
        this.objectId = objectId;
        this.objectType = objectType;
        this.action = action;
    }

    public ActionCompleteEvent(JSONObject object){
        objectId = object.getInt(Properties.objectId.name());
        objectType = object.getString(Properties.objectType.name());
        action = object.getString(Properties.action.name());
    }

    public int getObjectId(){
        return objectId;
    }

    public String getObjectType(){
        return objectType;
    }

    public String getAction() { return action; }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onActionComplete(this);
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
            case objectId: return objectId;
            case objectType: return objectType;
            case action: return action;
        }
        return null;
    }
}
