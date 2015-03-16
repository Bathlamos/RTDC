package rtdc.core.event;

import rtdc.core.json.JSONObject;

public class UpdateCompleteEvent extends Event<UpdateCompleteEvent.UpdateCompleteHandler> {

    public static final EventType<UpdateCompleteHandler> TYPE = EventType.build("updateComplete");
    public interface UpdateCompleteHandler extends EventHandler{ public void onUpdateComplete(UpdateCompleteEvent event);}

    UpdateCompleteEvent(){
        this(new JSONObject("{}"));
    }

    public UpdateCompleteEvent(JSONObject jsonObject){
        super(TYPE, jsonObject);
    }

    @Override
    void fire(UpdateCompleteHandler handler) {
        handler.onUpdateComplete(this);
    }
}
