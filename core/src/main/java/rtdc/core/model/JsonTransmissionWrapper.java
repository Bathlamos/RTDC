package rtdc.core.model;

import rtdc.core.json.JSONObject;

public class JsonTransmissionWrapper extends JSONObject {

    public JsonTransmissionWrapper(String json) {
        super(json);
    }

    public JsonTransmissionWrapper(Throwable exception) {
        setStatus(exception.getClass().getSimpleName());
    }

    public JSONObject getData(){
        return optJSONObject("data");
    }
    public void setData(JSONObject data) {
        put("data", data);
    }

    public String getStatus(){
        return optString("status");
    }
    public void setStatus( String status) {
        put("status", status);
    }

}
