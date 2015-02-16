package rtdc.core.model;


import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

public class AuthenticationInformation extends JSONObject {

    public AuthenticationInformation(){}

    public AuthenticationInformation(String json) throws JSONException{
        super(json);
    }

    public String getUsername(){
        return optString("username");
    }
    public void setUsername(String username){
        try {
            putOpt("username", username);
        }catch(JSONException e){}
    }

    public String getPassword(){
        return optString("password");
    }
    public void setPassword(String password){
        try {
            putOpt("password", password);
        }catch(JSONException e){}
    }

}
