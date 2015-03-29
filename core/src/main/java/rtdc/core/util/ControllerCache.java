package rtdc.core.util;

import java.util.HashMap;
import java.util.Map;

public class ControllerCache {

    private static final Map<String, Object> cache = new HashMap<>();

    public static void put(String identifier, Object value){
        cache.put(identifier, value);
    }

    public static Object retrieve(String identifier){
        return cache.get(identifier);
    }

}
