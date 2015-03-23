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

    protected JSONArray toJsonArray(Iterable iterable){
        JSONArray array = new JSONArray();
        for(Object o: iterable)
            array.put(o);
        return array;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("type", getType());
        augmentJsonObject(object);
        return object.toString();
    }
}
