package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class LogoutEvent extends Event<LogoutEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("logoutEvent");

    public enum Properties implements ObjectProperty<LogoutEvent>{}

    public interface Handler extends EventHandler{ void onLogout(LogoutEvent event); }

    void fire() {
        for(Handler handler: getHandlers(TYPE))
            handler.onLogout(this);
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
        return null;
    }
}
