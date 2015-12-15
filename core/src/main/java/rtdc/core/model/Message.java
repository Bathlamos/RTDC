/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.core.model;

import rtdc.core.i18n.ResBundle;
import rtdc.core.json.JSONObject;
import rtdc.core.util.Stringifier;

import java.util.Date;

public class Message extends RootObject {

    public enum Properties implements ObjectProperty<Message> {
        id,
        sender,
        senderID,
        receiver,
        receiverID,
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
                        case sent: return ResBundle.get().sent();
                        case delivered: return ResBundle.get().delivered();
                        case read: return ResBundle.get().read();
                        default: return status.name();
                    }
                }
            };
        }
    }

    private int id = -1;
    private User sender;
    private int senderID = -1;
    private User receiver;
    private int receiverID = -1;
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
            case senderID: return senderID;
            case receiver: return receiver;
            case receiverID: return receiverID;
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
        if(sender != null)
            this.senderID = sender.getId();
    }

    public int getSenderID() { return senderID; }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public User getReceiver() { return receiver; }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
        if(receiver != null)
            this.receiverID = receiver.getId();
    }

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
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