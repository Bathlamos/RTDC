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

import com.google.common.collect.ImmutableSet;
import rtdc.core.i18n.ResBundle;
import rtdc.core.json.JSONObject;
import rtdc.core.model.RootObject;

import java.util.HashMap;
import java.util.Map;

public abstract class Event<T extends EventHandler> extends RootObject {

    private static Map<EventType, EventAggregator> handlers = new HashMap<EventType, EventAggregator>();

    public static void fire(JSONObject object){
        String type = object.optString("_type");
        if(type == null)
            new ErrorEvent(ResBundle.get().unknownMessageType(object.toString())).fire();
        else{
            Event e = null;
            if(type.equalsIgnoreCase(AuthenticationEvent.TYPE.getName()))
                e = new AuthenticationEvent(object);
            else if(type.equalsIgnoreCase(ErrorEvent.TYPE.getName()))
                e = new ErrorEvent(object);
            else if(type.equalsIgnoreCase(FetchUnitsEvent.TYPE.getName()))
                e = new FetchUnitsEvent(object);
            else if(type.equalsIgnoreCase(FetchUsersEvent.TYPE.getName()))
                e = new FetchUsersEvent(object);
            else if(type.equalsIgnoreCase(FetchUserEvent.TYPE.getName()))
                e = new FetchUserEvent(object);
            else if(type.equalsIgnoreCase(FetchActionsEvent.TYPE.getName()))
                e = new FetchActionsEvent(object);
            else if(type.equalsIgnoreCase(FetchMessagesEvent.TYPE.getName()))
                e = new FetchMessagesEvent(object);
            else if(type.equalsIgnoreCase(MessageSavedEvent.TYPE.getName()))
                e = new MessageSavedEvent(object);
            else if(type.equalsIgnoreCase(FetchRecentContactsEvent.TYPE.getName()))
                e = new FetchRecentContactsEvent(object);
            else if(type.equalsIgnoreCase(ActionCompleteEvent.TYPE.getName()))
                e = new ActionCompleteEvent(object);
            else if(type.equalsIgnoreCase(SessionExpiredEvent.TYPE.getName()))
                e = new SessionExpiredEvent();
            else if(type.equalsIgnoreCase(LogoutEvent.TYPE.getName()))
                e = new LogoutEvent();

            if( e != null)
                e.fire();
            else
                throw new RuntimeException(ResBundle.get().unregisteredEvent(object.toString()));
        }
    }

    public static <T extends EventHandler> void subscribe(EventType<T> eventType, T eventHandler){
        if(!handlers.containsKey(eventType))
            handlers.put(eventType, new EventAggregator<T>());
        handlers.get(eventType).addHandler(eventHandler);
    }

    public static <T extends EventHandler> void unsubscribe(EventType<T> eventType, T eventHandler){
        if(handlers.containsKey(eventType))
            handlers.get(eventType).removeHandler(eventHandler);
    }

    protected <T extends EventHandler> ImmutableSet<T> getHandlers(EventType<T> type){
        EventAggregator eventAggregator = handlers.get(type);
        if(eventAggregator == null)
            return ImmutableSet.of();
        else
            return eventAggregator.getHandlers();
    }

    abstract void fire();
}
