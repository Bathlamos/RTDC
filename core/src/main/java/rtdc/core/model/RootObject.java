package rtdc.core.model;

import com.google.common.base.Function;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;

import java.util.ArrayList;

public abstract class RootObject {

    public abstract void augmentJsonObject(JSONObject object);

    public abstract String getType();

    protected <T> ArrayList<T> parseJsonArray(JSONArray array, Function<JSONObject, T> function){
        ArrayList<T> arrayList = new ArrayList<T>();
        for(int i = 0; i < array.length(); i++)
            arrayList.add(function.apply(array.getJSONObject(i)));
        return arrayList;
    }

    protected JSONArray toJsonArray(Iterable<? extends RootObject> iterable){
        JSONArray array = new JSONArray();
        for(RootObject o: iterable)
            array.put(o.toJsonObject());
        return array;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }

    public JSONObject toJsonObject(){
        JSONObject object = new JSONObject();
        object.put("type", getType());
        augmentJsonObject(object);
        return object;
    }
}
