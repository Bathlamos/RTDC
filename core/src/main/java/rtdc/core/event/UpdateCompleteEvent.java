package rtdc.core.event;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;

import java.util.Set;

public class UpdateCompleteEvent extends Event {

    public static final String TYPE = "updateComplete";

    public interface UpdateCompleteHandler{ public void onUpdateComplete(UpdateCompleteEvent event);}

    private static final Set<Property> objectProperties = Sets.newHashSet();

    UpdateCompleteEvent(){
        this(new JSONObject("{}"));
    }

    public UpdateCompleteEvent(JSONObject jsonObject){
        super(TYPE, objectProperties, jsonObject);
    }

    public static void subscribe(UpdateCompleteHandler handler){
        handlers.add(handler);
    }

    public static void unsubscribe(UpdateCompleteHandler handler){
        handlers.remove(handler);
    }

    public void fire(){
        for(Object o: handlers)
            ((UpdateCompleteHandler) o).onUpdateComplete(this);
    }
}
