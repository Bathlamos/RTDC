package rtdc.core.model;

import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONObject;

public class JsonTransmissionWrapper extends JSONObject {

    public JsonTransmissionWrapper() {
        setStatus("success");
        setNull();
    }
    public JsonTransmissionWrapper(String json) {
        super(json);
    }
    public JsonTransmissionWrapper(JSONObject element) {
        setStatus("success");
        setData(element);
    }
    public JsonTransmissionWrapper(JSONArray element) {
        setStatus("success");
        setData(element);
    }

    public JsonTransmissionWrapper(Throwable exception) {
        setStatus(exception.getClass().getSimpleName());
        setDescription(exception.getMessage());
    }

    public JSONObject getData(){
        return optJSONObject("data");
    }
    public JSONArray getDataAsJSONArray(){
        return optJSONArray("data");
    }
    public void setData(JSONObject data) {
        put("data", data);
    }
    public void setData(JSONArray data) {
        put("data", data);
    }
    public void setNull() {
        put("data", JSONObject.NULL);
    }

    public String getStatus(){
        return optString("status");
    }
    public void setStatus(String status) {
        put("status", status);
    }

    public String getDescription(){
        return optString("description");
    }
    public void setDescription(String description) {
        put("description", description);
    }

}
