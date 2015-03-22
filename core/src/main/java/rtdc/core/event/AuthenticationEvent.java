package rtdc.core.event;

import rtdc.core.json.JSONObject;
import rtdc.core.model.DataType;
import rtdc.core.model.Field;
import rtdc.core.model.RtdcObject;
import rtdc.core.model.User;

public class AuthenticationEvent extends Event<AuthenticationEvent.AuthenticationHandler> {

    public static final DataType<AuthenticationEvent> TYPE = DataType.extend(RtdcObject.TYPE, "authentication",
            AuthenticationEvent.class,
            new Field("user", User.TYPE),
            new Field("auth_token", DataType.STRING));

    public interface AuthenticationHandler extends EventHandler<AuthenticationEvent>{
        public void onAuthenticate(AuthenticationEvent event);
    }

    public AuthenticationEvent(User user, String authenticationToken){
        this(new JSONObject("{}"));
        setProperty("user", user);
        setProperty("auth_token", authenticationToken);
    }

    public AuthenticationEvent(JSONObject jsonObject){
        super(jsonObject);
    }

    @Override
    public DataType getType() {
        return TYPE;
    }

    public User getUser(){
        return (User) getProperty("user");
    }

    public String getAuthenticationToken(){
        return (String) getProperty("auth_token");
    }

    void fire() {
        for(AuthenticationHandler handler: getHandlers(TYPE))
            handler.onAuthenticate(this);
    }
}
