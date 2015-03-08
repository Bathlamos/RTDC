package rtdc.core.event;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;

import java.util.HashSet;
import java.util.Set;

public class ErrorEvent extends Event {

    public static final String TYPE = "error";

    public interface ErrorHandler{ public void onError(ErrorEvent event);}

    public static final Property DESCRIPTION = new Property("description", DataType.STRING);
    private static final Set<Property> objectProperties = Sets.newHashSet(DESCRIPTION);

    public ErrorEvent(String description){
        this(new JSONObject("{}"));
        setProperty(DESCRIPTION, description);
    }

    public ErrorEvent(JSONObject jsonObject){
        super(TYPE, objectProperties, jsonObject);
    }

    public String getDescription(){
        return (String) getProperty(DESCRIPTION);
    }

    public static void subscribe(ErrorHandler handler){
        handlers.add(handler);
    }

    public static void unsubscribe(ErrorHandler handler){
        handlers.remove(handler);
    }

    public void fire(){
        for(Object o: handlers)
            ((ErrorHandler) o).onError(this);
    }

}
