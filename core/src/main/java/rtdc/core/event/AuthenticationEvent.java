package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class AuthenticationEvent extends Event<AuthenticationEvent.AuthenticationHandler> {

    public static final EventType<AuthenticationHandler> TYPE = new EventType<AuthenticationHandler>("authenticationEvent");

    public enum Properties implements ObjectProperty<AuthenticationEvent>{
        user,
        authenticationToken
    }

    private final User user;
    private final String authenticationToken;

    public interface AuthenticationHandler extends EventHandler{
        public void onAuthenticate(AuthenticationEvent event);
    }

    public AuthenticationEvent(User user, String authenticationToken){
        this.user = user;
        this.authenticationToken = authenticationToken;
    }

    public AuthenticationEvent(JSONObject object){
        user = new User(object.getJSONObject(Properties.user.name()));
        authenticationToken = object.optString(Properties.authenticationToken.name());
    }
    
    public User getUser(){
        return user;
    }

    public String getAuthenticationToken(){
        return authenticationToken;
    }

    void fire() {
        for(AuthenticationHandler handler: getHandlers(TYPE))
            handler.onAuthenticate(this);
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return TYPE.getName();
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case user: return user;
            case authenticationToken: return authenticationToken;
        }
        return null;
    }
}
