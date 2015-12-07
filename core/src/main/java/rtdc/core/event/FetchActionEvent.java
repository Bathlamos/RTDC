package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.Action;
import rtdc.core.model.ObjectProperty;

public class FetchActionEvent extends Event<FetchActionEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<>("fetchActionEvent");

    public interface Handler extends EventHandler { void onActionFetched(FetchActionEvent event);}

    public enum Properties implements ObjectProperty<FetchActionEvent>{
        action
    }

    private final Action action;

    public FetchActionEvent(Action action) {
        this.action = new Action(action.toJsonObject());
    }

    public FetchActionEvent(JSONObject object) {
        this.action = new Action(object);
    }

    public Action getAction() {
        return action;
    }

    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onActionFetched(this);
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
            case action : return action;
        }
        return null;
    }
}
