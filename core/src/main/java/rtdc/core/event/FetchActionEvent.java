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
import rtdc.core.model.Action;
import rtdc.core.model.ObjectProperty;

public class FetchActionEvent extends Event<FetchActionEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<>("fetchActionEvent");

    public interface Handler extends EventHandler { void onActionFetched(FetchActionEvent event);}

    public enum Properties implements ObjectProperty<FetchActionEvent>{
        action
    }

    private final Action action;

    public FetchActionEvent(Action action) {
        this.action = new Action(action.toJsonObject());
    }

    public FetchActionEvent(JSONObject object) {
        this.action = new Action(object);
    }

    public Action getAction() {
        return action;
    }

    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onActionFetched(this);
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
            case action : return action;
        }
        return null;
    }
}
