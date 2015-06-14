package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class FetchUsersEvent extends Event<FetchUsersEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchUsersEvent");

    public interface Handler extends EventHandler{ void onUsersFetched(FetchUsersEvent event);}

    public enum Properties implements ObjectProperty<FetchUsersEvent>{
        users
    }

    private final ImmutableSet<User> users;

    public FetchUsersEvent(Iterable<User> units){
        this.users = ImmutableSet.copyOf(units);
    }

    public FetchUsersEvent(JSONObject object){
        users = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.users.name()), new Function<JSONObject, User>() {
            @Override
            public User apply(JSONObject input) {
                return new User(input);
            }
        }));
    }

    public ImmutableSet<User> getUsers(){
        return users;
    }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onUsersFetched(this);
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
            case users: return users;
        }
        return null;
    }
}
