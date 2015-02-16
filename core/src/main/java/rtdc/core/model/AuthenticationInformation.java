package rtdc.core.model;

import rtdc.core.json.JSONObject;

public class AuthenticationInformation extends JSONObject {

    public AuthenticationInformation(){}

    public AuthenticationInformation(String json){
        super(json);
    }

    public String getUsername(){
        return optString("username");
    }
    public void setUsername(String username){
        put("username", username);
    }

    public String getPassword(){
        return optString("password");
    }
    public void setPassword(String password) {
        putOpt("password", password);
    }

}
