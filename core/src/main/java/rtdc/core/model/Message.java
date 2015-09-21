package rtdc.core.model;

import rtdc.core.json.JSONObject;
import rtdc.core.util.Stringifier;

import java.util.Date;

public class Message extends RootObject {

    public enum Properties implements ObjectProperty<Message> {
        id,
        sender,
        receiver,
        status,
        timeSent,
        content
    }

    public enum Status {
        sent,
        delivered,
        read;

        public static Stringifier<Status> getStringifier(){
            return new Stringifier<Status>() {
                @Override
                public String toString(Status status) {
                    switch(status){
                        case sent: return "Sent";
                        case delivered: return "Delivered";
                        case read: return "Read";
                        default: return status.name();
                    }
                }
            };
        }
    }

    private int id;
    private User sender;
    private User receiver;
    private Status status;
    private Date timeSent;
    private String content;

    public Message(){}

    public Message(JSONObject object){
        setId(object.optInt(Properties.id.name()));
        setSender(new User(object.getJSONObject(Properties.sender.name())));
        setReceiver(new User(object.getJSONObject(Properties.receiver.name())));
        setStatus(Status.valueOf(object.optString(Properties.status.name())));
        setTimeSent(new Date(object.getLong(Properties.timeSent.name())));

        if(object.has(Properties.content.name()))
         setContent(object.optString(Properties.content.name()));
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return "message";
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case id: return id;
            case sender: return sender;
            case receiver: return receiver;
            case status: return status;
            case timeSent: return timeSent;
            case content: return content;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(Date timeSent) {
        this.timeSent = timeSent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}