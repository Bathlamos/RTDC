package rtdc.web.server.util;

import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Collection;
import java.util.Vector;

public class Util {

    public static String getHttpRequestData(HttpServletRequest req){
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jb.toString();
    }

    public static <T extends JSONObject> JSONArray asJSONArray(Iterable<T> list){
        JSONArray array = new JSONArray();
        for(T t: list)
            array.put(t);
        return array;
    }

    public static <T extends JSONObject> Iterable<T> asList(JSONArray array, Collection<T> collection){
        for(int i = array.length() - 1; i >= 0; i++)
            collection.add((T) array.get(i));
        return collection;
    }

}
