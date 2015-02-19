package rtdc.core.util;

import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;

import java.util.List;

public class Util {

    public static <T extends JSONObject> JSONArray asJSONArray(Iterable<T> list){
        JSONArray array = new JSONArray();
        for(T t: list)
            array.put(t);
        return array;
    }

    public static <T extends JSONObject> List<T> asList(JSONArray array, List<T> list){
        for(int i = array.length() - 1; i >= 0; i++)
            list.add((T) array.get(i));
        return list;
    }

}
