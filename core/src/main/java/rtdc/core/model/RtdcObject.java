package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import java.util.Map;
import java.util.Set;

public abstract class RtdcObject {

    protected final JSONObject jsonObject;

    public RtdcObject(){
        jsonObject = new JSONObject();
    }

    public RtdcObject(String json){
        jsonObject = new JSONObject(json);
    }

    protected boolean expectBoolean(String propertyName){
        try{
            jsonObject.getBoolean(propertyName);
            return true;
        }catch(JSONException e){
            return false;
        }
    }

    protected boolean expectInt(String propertyName){
        try{
            jsonObject.getInt(propertyName);
            return true;
        }catch(JSONException e){
            return false;
        }
    }

    protected boolean expectLong(String propertyName){
        try{
            jsonObject.getLong(propertyName);
            return true;
        }catch(JSONException e){
            return false;
        }
    }

    protected boolean expectString(String propertyName){
        try{
            jsonObject.getString(propertyName);
            return true;
        }catch(JSONException e){
            return false;
        }
    }

    protected boolean expectJsonArray(String propertyName){
        try{
            jsonObject.getJSONArray(propertyName);
            return true;
        }catch(JSONException e){
            return false;
        }
    }

    protected boolean expectJsonObject(String propertyName){
        try{
            jsonObject.getJSONObject(propertyName);
            return true;
        }catch(JSONException e){
            return false;
        }
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

    public abstract Set<String> validateProperty(String property);

    public abstract Map<String, String> validateAll();

}
