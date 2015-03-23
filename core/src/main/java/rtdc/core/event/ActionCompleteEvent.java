package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;

public class ActionCompleteEvent extends Event<ActionCompleteEvent.ActionCompleteHandler> {

    public static final EventType<ActionCompleteHandler> TYPE = new EventType<ActionCompleteHandler>("actionCompleteEvent");

    public interface ActionCompleteHandler extends EventHandler{ public void onActionComplete(ActionCompleteEvent event);}

    public enum Properties{
        objectId,
        objectType
    }

    private final String objectType;
    private final int objectId;

    public ActionCompleteEvent(int objectId, String objectType){
        this.objectId = objectId;
        this.objectType = objectType;
    }

    public ActionCompleteEvent(JSONObject object){
        objectId = object.getInt(Properties.objectId.name());
        objectType = object.getString(Properties.objectType.name());
    }

    public int getObjectId(){
        return objectId;
    }

    public String getObjectType(){
        return objectType;
    }


    public void fire(){
        for(ActionCompleteHandler handler: getHandlers(TYPE))
            handler.onActionComplete(this);
    }

    @Override
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.objectId.name(), objectId);
        object.put(Properties.objectType.name(), objectType);
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }
}
