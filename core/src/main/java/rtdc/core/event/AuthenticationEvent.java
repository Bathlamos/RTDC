package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectType;
import rtdc.core.model.User;
import rtdc.core.model.Property;

import static rtdc.core.model.DataType;

public class AuthenticationEvent extends Event<AuthenticationEvent.AuthenticationHandler> {

    public static final ObjectType<AuthenticationHandler> TYPE = ObjectType.build("authentication");
    public interface AuthenticationHandler extends EventHandler{ public void onAuthenticate(AuthenticationEvent event);}

    public static final Property USER = new Property("user", DataType.USER),
            AUTH_TOKEN = new Property("auth_token", DataType.STRING);

    public AuthenticationEvent(User user, String authenticationToken){
        this(new JSONObject("{}"));
        setProperty(USER, user);
        setProperty(AUTH_TOKEN, authenticationToken);
    }

    public AuthenticationEvent(JSONObject jsonObject){
        super(TYPE, jsonObject, USER, AUTH_TOKEN);
    }

    public User getUser(){
        return (User) getProperty(USER);
    }

    public String getAuthenticationToken(){
        return (String) getProperty(AUTH_TOKEN);
    }

    void fire() {
        for(AuthenticationHandler handler: getHandlers(TYPE))
            handler.onAuthenticate(this);
    }
}
