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
