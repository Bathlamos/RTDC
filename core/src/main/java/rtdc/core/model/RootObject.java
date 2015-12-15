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

import com.google.common.base.Function;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * The root class of all models.
 * This class ensures that the objects can be instantiated from JSON, and converted to JSON automatically,
 * no matter the underlying platform.
 */
public abstract class RootObject {

    /**
     * @return the properties (i.e. inner variables) of the class
     */
    public abstract ObjectProperty[] getProperties();

    /**
     * @return A unique String identifier describing the class
     */
    public abstract String getType();

    /**
     * @param property A property of the class
     * @return The value associated to the property, in the instance
     */
    public abstract Object getValue(ObjectProperty property);

    /**
     * Utility method to quickly convert a JSON array of objects into a list of the associated object type
     * @param array The JSON array to convert
     * @param function The function, converting a JSON object of type T into its proper Java instance
     * @param <T> The type of the object to convert
     * @return A list of the converted objects
     */
    protected <T> ArrayList<T> parseJsonArray(JSONArray array, Function<JSONObject, T> function){
        ArrayList<T> arrayList = new ArrayList<T>();
        for(int i = 0; i < array.length(); i++)
            arrayList.add(function.apply(array.getJSONObject(i)));
        return arrayList;
    }

    /**
     * Utility method to quickly convert an array or list of objects into their JSON representation
     * @param iterable The list of objects to convert
     * @return A JSON String of the list
     */
    protected JSONArray toJsonArray(Iterable iterable){
        JSONArray array = new JSONArray();
        for(Object o: iterable) {
            if(o instanceof RootObject)
                array.put(((RootObject) o).toJsonObject());
            else
                array.put(o);
        }
        return array;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }

    /**
     * Convert the object to JSON
     * @return A JSON String of the object
     */
    public JSONObject toJsonObject(){
        JSONObject object = new JSONObject();
        object.put("_type", getType());
        for(ObjectProperty p: getProperties()){
            Object o = getValue(p);
            if(o == null)
                ;//Do nothing ~> we want the JSON to be light
            else if(o instanceof Iterable)
                object.put(p.name(), toJsonArray((Iterable) o));
            else if(o instanceof RootObject)
                object.put(p.name(), ((RootObject) o).toJsonObject());
            else if(o instanceof Date)
                object.put(p.name(), ((Date) o).getTime());
            else
                object.put(p.name(), o);
        }
        return object;
    }
}
