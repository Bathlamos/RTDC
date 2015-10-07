package rtdc.core.event;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Message;
import rtdc.core.model.ObjectProperty;

public class FetchRecentContactsEvent extends Event<FetchRecentContactsEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("fetchRecentContactsEvent");

    public interface Handler extends EventHandler{ void onRecentContactsFetched(FetchRecentContactsEvent event);}

    public enum Properties implements ObjectProperty<FetchRecentContactsEvent>{
        messages
    }

    private final ImmutableSet<Message> messages;

    public FetchRecentContactsEvent(Iterable<Message> messages){
        this.messages = ImmutableSet.copyOf(messages);
    }

    public FetchRecentContactsEvent(JSONObject object){
        messages = ImmutableSet.copyOf(parseJsonArray(object.getJSONArray(Properties.messages.name()), new Function<JSONObject, Message>() {
            @Override
            public Message apply(JSONObject input) {
                return new Message(input);
            }
        }));
    }

    public ImmutableSet<Message> getRecentContacts(){
        return messages;
    }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onRecentContactsFetched(this);
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