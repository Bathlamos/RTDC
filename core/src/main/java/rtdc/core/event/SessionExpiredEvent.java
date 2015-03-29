package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class SessionExpiredEvent extends Event<SessionExpiredEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("sessionExpiredEvent");

    public enum Properties implements ObjectProperty<SessionExpiredEvent>{}

    public interface Handler extends EventHandler{ public void onSessionExpired(); }

    void fire() {
        for(Handler handler: getHandlers(TYPE))
            handler.onSessionExpired();
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
