package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.Message;
import rtdc.core.model.User;

public class FetchMessagesEvent extends Event<FetchMessagesEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchMessagesEvent");

    public interface Handler extends EventHandler{ void onMessagesFetched(FetchMessagesEvent event);}

    public enum Properties implements ObjectProperty<FetchMessagesEvent>{
        user1,
        user2,
        messages
    }

    private User user1;
    private User user2;
    private final ImmutableSet<Message> messages;

    public FetchMessagesEvent(User user1, User user2, Iterable<Message> messages){
        this.user1 = user1;
        this.user2 = user2;
        this.messages = ImmutableSet.copyOf(messages);
    }

    public FetchMessagesEvent(JSONObject object){
        user1 = new User(object.getJSONObject(Properties.user1.name()));
        user2 = new User(object.getJSONObject(Properties.user2.name()));
        messages = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.messages.name()), new Function<JSONObject, Message>() {
            @Override
            public Message apply(JSONObject input) {
                return new Message(input);
            }
        }));
    }

    public User getUser1() { return user1; }

    public User getUser2() { return user2; }

    public ImmutableSet<Message> getMessages(){
        return messages;
    }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onMessagesFetched(this);
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
            case user1: return user1;
            case user2: return user2;
            case messages: return messages;
        }
        return null;
    }
}
