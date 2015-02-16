package rtdc.core.model;

import com.goodow.realtime.json.impl.JreJsonObject;

public class AuthenticationInformation extends JreJsonObject {

    public String getUsername() {
        return getString("username");
    }
    public void setUsername(String username) {
        set("username", username);
    }

    public String getPassword() {
        return getString("password");
    }
    public void setPassword(String password) {
        set("password", password);
    }


}
