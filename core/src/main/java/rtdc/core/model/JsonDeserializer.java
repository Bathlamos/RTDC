package rtdc.core.model;

import rtdc.core.event.*;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

public final class JsonDeserializer {

    public static <T> T instantiateObject(Field field, JSONObject object) throws JSONException{
        String type = object.optString("type");
        if(field != null)
            type = field.getDataType().getName();
        if(type == null || !type.equalsIgnoreCase(object.optString("type")))
            throw new JSONException("Object was not of the desired type");
        if(ErrorEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new ErrorEvent(object);
        if(AuthenticationEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new AuthenticationEvent(object);
        if(UpdateCompleteEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new UpdateCompleteEvent(object);
        if(FetchUnitsEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new FetchUnitsEvent(object);
        if(User.TYPE.getName().equalsIgnoreCase(type))
            return (T) new User(object);
        return null;
    }

}