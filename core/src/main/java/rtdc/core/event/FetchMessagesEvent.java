package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.Message;

public class FetchMessagesEvent extends Event<FetchMessagesEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchMessagesEvent");

    public interface Handler extends EventHandler{ void onMessagesFetched(FetchMessagesEvent event);}

    public enum Properties implements ObjectProperty<FetchMessagesEvent>{
        messages
    }

    private final ImmutableSet<Message> messages;

    public FetchMessagesEvent(Iterable<Message> messages){
        this.messages = ImmutableSet.copyOf(messages);
    }

    public FetchMessagesEvent(JSONObject object){
        messages = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.messages.name()), new Function<JSONObject, Message>() {
            @Override
            public Message apply(JSONObject input) {
                return new Message(input);
            }
        }));
    }

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
            case messages: return messages;
        }
        return null;
    }
}
