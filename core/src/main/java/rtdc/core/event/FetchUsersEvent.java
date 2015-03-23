package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Unit;
import rtdc.core.model.User;

public class FetchUsersEvent extends Event<FetchUsersEvent.FetchUsersHandler> {

    public static final EventType<FetchUsersHandler> TYPE = new EventType<FetchUsersHandler>("fetchUsersEvent");

    public interface FetchUsersHandler extends EventHandler{ public void onUsersFetched(FetchUsersEvent event);}

    public enum Properties{
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
        for(FetchUsersHandler handler: getHandlers(TYPE))
            handler.onUsersFetched(this);
    }

    @Override
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.users.name(), toJsonArray(users));
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }
}
