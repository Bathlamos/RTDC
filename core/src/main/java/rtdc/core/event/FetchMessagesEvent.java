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
