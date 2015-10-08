package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;

import java.util.Date;

public class MessageSavedEvent extends Event<ActionCompleteEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("messageSavedEvent");

    public interface Handler extends EventHandler{ void onMessageSaved(MessageSavedEvent event);}

    public enum Properties implements ObjectProperty<ActionCompleteEvent>{
        messageId,
        timeSent,
    }

    private final Date timeSent;
    private final int messageId;

    public MessageSavedEvent(int messageId, Date timeSent){
        this.messageId = messageId;
        this.timeSent = timeSent;
    }

    public MessageSavedEvent(JSONObject object){
        messageId = object.getInt(Properties.messageId.name());

        // It is possible that the date was converted to a Long, as this is how its stored in the database
        if(object.get(Properties.timeSent.name()) instanceof Date)
            timeSent = (Date) object.get(Properties.timeSent.name());
        else
            timeSent = new Date((Long) object.get(Properties.timeSent.name()));
    }

    public int getMessageId(){
        return messageId;
    }

    public Date getTimeSent(){
        return timeSent;
    }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onMessageSaved(this);
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
            case messageId: return messageId;
            case timeSent: return timeSent;
        }
        return null;
    }
}
