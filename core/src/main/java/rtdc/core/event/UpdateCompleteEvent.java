package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectType;

public class UpdateCompleteEvent extends Event<UpdateCompleteEvent.UpdateCompleteHandler> {

    public static final ObjectType<UpdateCompleteHandler> UNIT_UPDATED = ObjectType.build("unitUpdated"),
            UNIT_DELETED = ObjectType.build("unitDeleted"),
            USER_UPDATED = ObjectType.build("userUpdated"),
            USER_DELETED = ObjectType.build("userDeleted");
    public interface UpdateCompleteHandler extends EventHandler{ public void onUpdateComplete(UpdateCompleteEvent event);}

    private String objectId;
    private ObjectType<UpdateCompleteHandler> eventType;

    public UpdateCompleteEvent(ObjectType<UpdateCompleteHandler> eventType, String objectId){
        this(new JSONObject("{}"));
        this.objectId = objectId;
        this.eventType = eventType;
    }

    public UpdateCompleteEvent(JSONObject jsonObject){
        super(UNIT_UPDATED, jsonObject);
    }

    @Override
    void fire() {
        for(UpdateCompleteHandler handler: getHandlers(UNIT_UPDATED))
            handler.onUpdateComplete(this);
    }

    public String getObjectId() {
        return objectId;
    }

    public ObjectType<UpdateCompleteHandler> getEventType() {
        return eventType;
    }
}
