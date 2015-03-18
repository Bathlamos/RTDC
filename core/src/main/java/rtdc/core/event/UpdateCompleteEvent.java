package rtdc.core.event;

import rtdc.core.json.JSONObject;

public class UpdateCompleteEvent extends Event<UpdateCompleteEvent.UpdateCompleteHandler> {

    public static final EventType<UpdateCompleteHandler> UNIT_UPDATED = EventType.build("unitUpdated"),
            UNIT_DELETED = EventType.build("unitDeleted"),
            USER_UPDATED = EventType.build("userUpdated"),
            USER_DELETED = EventType.build("userDeleted");
    public interface UpdateCompleteHandler extends EventHandler{ public void onUpdateComplete(UpdateCompleteEvent event);}

    private String objectId;
    private EventType<UpdateCompleteHandler> eventType;

    public UpdateCompleteEvent(EventType<UpdateCompleteHandler> eventType, String objectId){
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

    public EventType<UpdateCompleteHandler> getEventType() {
        return eventType;
    }
}
