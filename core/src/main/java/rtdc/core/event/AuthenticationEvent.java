package rtdc.core.event;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;

import java.util.Set;

public class AuthenticationEvent extends Event {

    public static final String TYPE = "authentication";

    public interface AuthenticationHandler{ public void onAuthenticate(AuthenticationEvent event);}

    public static final Property USER = new Property("user", DataType.USER),
            AUTH_TOKEN = new Property("auth_token", DataType.STRING);

    private static final Set<Property> objectProperties = Sets.newHashSet(USER, AUTH_TOKEN);

    AuthenticationEvent(){
        this(new JSONObject("{}"));
    }

    public AuthenticationEvent(User user, String authenticationToken){
        this(new JSONObject("{}"));
        setProperty(USER, user);
        setProperty(AUTH_TOKEN, authenticationToken);
    }

    public AuthenticationEvent(JSONObject jsonObject){
        super(TYPE, objectProperties, jsonObject);
    }

    public User getUser(){
        return (User) getProperty(USER);
    }

    public String getAuthenticationToken(){
        return (String) getProperty(AUTH_TOKEN);
    }

    public static void subscribe(AuthenticationHandler handler){
        handlers.add(handler);
    }

    public static void unsubscribe(AuthenticationHandler handler){
        handlers.remove(handler);
    }

    public void fire(){
        for(Object o: handlers)
            ((AuthenticationHandler) o).onAuthenticate(this);
    }
}
