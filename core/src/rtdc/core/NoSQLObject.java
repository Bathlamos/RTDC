package rtdc.core;

import java.util.HashMap;
import java.util.Map;

public class NoSQLObject{

    private Map<String, Object> content = new HashMap<>();

    public NoSQLObject(){

    }

    public NoSQLObject(Map<String, Object> content){
        addAll(content);
    }

    public Map<String, Object> getProperties(){
        return content;
    }

    public String getId(){
        return (String) content.get("_id");
    }

    public void setId(String id){
        content.put("_id", id);
    }

    public String getRev(){
        return (String) content.get("_rev");
    }

    public void setRev(String rev){
        content.put("_rev", rev);
    }

    protected void addAll(Map<String, Object> content){
        this.content.putAll(content);
    }

    protected void set(String key, Object value){
        content.put(key, value);
    }

    protected Object get(String key) {
        return content.get(key);
    }
}
