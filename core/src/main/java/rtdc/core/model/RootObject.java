package rtdc.core.model;

import com.google.common.base.Function;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public abstract class RootObject {

    public abstract ObjectProperty[] getProperties();

    public abstract String getType();

    public abstract Object getValue(ObjectProperty property);

    protected <T> ArrayList<T> parseJsonArray(JSONArray array, Function<JSONObject, T> function){
        ArrayList<T> arrayList = new ArrayList<T>();
        for(int i = 0; i < array.length(); i++)
            arrayList.add(function.apply(array.getJSONObject(i)));
        return arrayList;
    }

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
