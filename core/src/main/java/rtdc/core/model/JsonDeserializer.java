package rtdc.core.model;

import rtdc.core.event.*;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

public final class JsonDeserializer {

    public static <T> T instantiateObject(String type, JSONObject object) throws JSONException{
        if(!type.equalsIgnoreCase(object.optString("type")))
            throw new JSONException("Object was not of the desired type");
        if(ErrorEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new ErrorEvent(object);
        if(AuthenticationEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new AuthenticationEvent(object);
        if(UpdateCompleteEvent.UNIT_UPDATED.getName().equalsIgnoreCase(type))
            return (T) new UpdateCompleteEvent(object);
        if(FetchUnitsEvent.TYPE.getName().equalsIgnoreCase(type))
            return (T) new FetchUnitsEvent(object);
        if(User.TYPE.getName().equalsIgnoreCase(type))
            return (T) new User(object);
        return null;
    }

}
