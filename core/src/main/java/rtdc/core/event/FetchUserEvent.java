package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class FetchUserEvent extends Event<FetchUsersEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchUserEvent");

    public interface Handler extends EventHandler{ void onUserFetched(FetchUserEvent event);}

    public enum Properties implements ObjectProperty<FetchUsersEvent>{
        user
    }

    private final User user;

    public FetchUserEvent(User user){ this.user = user; }

    public FetchUserEvent(JSONObject object){
        user = new User(object.getJSONObject(Properties.user.name()));
    }

    public User getUser(){ return user; }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onUserFetched(this);
    }

    @Override
    public ObjectProperty[] getProperties() {
        return  Properties.values();
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case user: return user;
        }
        return null;
    }
}
