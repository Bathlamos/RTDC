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

public class ActionCompleteEvent extends Event<ActionCompleteEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("actionCompleteEvent");

    public interface Handler extends EventHandler{ void onActionComplete(ActionCompleteEvent event);}

    public enum Properties implements ObjectProperty<ActionCompleteEvent>{
        objectId,
        objectType,
        action
    }

    private final String objectType;
    private final int objectId;
    private final String action;

    public ActionCompleteEvent(int objectId, String objectType, String action){
        this.objectId = objectId;
        this.objectType = objectType;
        this.action = action;
    }

    public ActionCompleteEvent(JSONObject object){
        objectId = object.getInt(Properties.objectId.name());
        objectType = object.getString(Properties.objectType.name());
        action = object.getString(Properties.action.name());
    }

    public int getObjectId(){
        return objectId;
    }

    public String getObjectType(){
        return objectType;
    }

    public String getAction() { return action; }


    public void fire(){
        for(Handler handler: getHandlers(TYPE))
            handler.onActionComplete(this);
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
            case objectId: return objectId;
            case objectType: return objectType;
            case action: return action;
        }
        return null;
    }
}
