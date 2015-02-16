package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

public class User extends JSONObject {

    public User(){}

    public User(String json) throws JSONException{
        super(json);
    }

    public int getId(){
        return optInt("id");
    }
    public void setId(int id){
        put("id", id);
    }

    public String getSurname(){
        return optString("surname");
    }

    public void setSurname(String surname){
        put("surname", surname);
    }

    public String getFirstName(){
        return optString("firstName");
    }

    public void setFirstName(String firstName){
        put("firstName", firstName);
    }


}
