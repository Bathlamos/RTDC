package cc.legault.rtdc.core;

import java.util.Map;

public class Unit extends NoSQLObject {

    public Unit(String name){
        setName(name);
    }

    public Unit(Map<String, Object> properties){
        super(properties);
    }

    public String getName() {
        return (String) get("name");
    }

    public void setName(String name) {
       set("name", name);
    }
}
