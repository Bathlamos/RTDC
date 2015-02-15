package rtdc.core.model;

import com.goodow.realtime.json.impl.JreJsonObject;

public class Unit extends JreJsonObject {

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        set("name", name);
    }
}
