package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.ObjectProperty;
import rtdc.core.model.User;

public class AuthenticationEvent extends Event<AuthenticationEvent.Handler> {

    public static final EventType<Handler> TYPE = new EventType<Handler>("authenticationEvent");

    public enum Properties implements ObjectProperty<AuthenticationEvent>{
        user,
        authenticationToken,
        asteriskPassword
    }

    private final User user;
    private final String authenticationToken;
    private final String asteriskPassword;

    public interface Handler extends EventHandler{ void onAuthenticate(AuthenticationEvent event); }

    public AuthenticationEvent(User user, String authenticationToken, String asteriskPassword){
        this.user = user;
        this.authenticationToken = authenticationToken;
        this.asteriskPassword = asteriskPassword;
    }

    public AuthenticationEvent(JSONObject object){
        user = new User(object.getJSONObject(Properties.user.name()));
        authenticationToken = object.optString(Properties.authenticationToken.name());
        asteriskPassword = object.optString(Properties.asteriskPassword.name());
    }
    
    public User getUser(){
        return user;
    }

    public String getAuthenticationToken(){
        return authenticationToken;
    }

    public String getAsteriskPassword() { return asteriskPassword; }

    void fire() {
        for(Handler handler: getHandlers(TYPE))
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
            case asteriskPassword: return asteriskPassword;
        }
        return null;
    }
}
