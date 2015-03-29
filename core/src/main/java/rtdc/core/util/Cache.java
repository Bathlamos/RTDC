package rtdc.core.util;

import java.util.HashMap;

public class Cache {

    private HashMap<String,Object> map = new HashMap<>();

    private Cache() {}

    private static Cache INSTANCE = null;

    public static Cache getInstance(){
        if (INSTANCE == null)
            INSTANCE = new Cache();
        return INSTANCE;
    }

    public void put(String key, Object object){
        map.put(key, object);
    }

    public Object retrieve(String key){
       return  map.remove(key);
    }
}
