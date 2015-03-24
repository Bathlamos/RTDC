package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.User;

public class FetchActionsEvent extends Event<FetchActionsEvent.FetchActionsHandler> {

    public static final EventType<FetchActionsHandler> TYPE = new EventType<FetchActionsHandler>("fetchActionsEvent");

    public interface FetchActionsHandler extends EventHandler{ public void onActionsFetched(FetchActionsEvent event);}

    public enum Properties{
        actions
    }

    private final ImmutableSet<Action> actions;

    public FetchActionsEvent(Iterable<Action> actions){
        this.actions = ImmutableSet.copyOf(actions);
    }

    public FetchActionsEvent(JSONObject object){
        actions = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.actions.name()), new Function<JSONObject, Action>() {
            @Override
            public Action apply(JSONObject input) {
                return new Action(input);
            }
        }));
    }

    public ImmutableSet<Action> getActions(){
        return actions;
    }


    public void fire(){
        for(FetchActionsHandler handler: getHandlers(TYPE))
            handler.onActionsFetched(this);
    }

    @Override
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.actions.name(), toJsonArray(actions));
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }
}
