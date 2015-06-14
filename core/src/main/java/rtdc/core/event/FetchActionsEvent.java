package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.ObjectProperty;

public class FetchActionsEvent extends Event<FetchActionsEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchActionsEvent");

    public interface Handler extends EventHandler{ void onActionsFetched(FetchActionsEvent event);}

    public enum Properties implements ObjectProperty<FetchActionsEvent>{
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
        for(Handler handler: getHandlers(TYPE))
            handler.onActionsFetched(this);
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
            case actions: return actions;
        }
        return null;
    }
}
