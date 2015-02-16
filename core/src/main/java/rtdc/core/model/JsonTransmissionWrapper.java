package rtdc.core.model;

import com.goodow.realtime.json.JsonElement;
import com.goodow.realtime.json.impl.JreJsonObject;

public class JsonTransmissionWrapper extends JreJsonObject {

    public JsonTransmissionWrapper(JsonElement data){
        setData(data);
        setStatus("success");
    }

    public JsonTransmissionWrapper(Throwable exception){
        setStatus(exception.getClass().getSimpleName());
    }

    public JsonElement getData() {
        return getObject("data");
    }

    public void setData(JsonElement data) {
        set("data", data);
    }

    public String getStatus() {
        return getString("status");
    }

    public void setStatus(String status) {
        set("status", status);
    }

}
