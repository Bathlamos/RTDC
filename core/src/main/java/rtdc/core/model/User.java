package rtdc.core.model;

import rtdc.core.json.JSONObject;

import java.util.Map;
import java.util.Set;

public class User extends RtdcObject {

    protected JSONObject jsonObject = new JSONObject();

    public static final String ID = "user_id",
            USERNAME = "username",
            LAST_NAME = "lastName",
            EMAIL = "email",
            PHONE = "phone",
            FIRST_NAME = "firstName",
            PERMISSION = "permission",
            ROLE = "role",
            UNIT = "unit",
            AUTH_TOKEN = "authenticationToken";

    public int getId(){
        return jsonObject.optInt(ID);
    }
    public void setId(int id){jsonObject.put(ID, id);}

    public String getUsername(){
        return jsonObject.optString(USERNAME);
    }
    public void setUsername(String username){
        jsonObject.put(USERNAME, username);
    }

    public String getLastName(){
        return jsonObject.optString(LAST_NAME);
    }
    public void setLastName(String lastName){
        jsonObject.put(LAST_NAME, lastName);
    }

    public String getFirstName(){
        return jsonObject.optString(FIRST_NAME);
    }
    public void setFirstName(String firstName){
        jsonObject.put(FIRST_NAME, firstName);
    }

    public String getEmail(){
        return jsonObject.optString(EMAIL);
    }
    public void setEmail(String email){
        jsonObject.put(EMAIL, email);
    }

    public long getPhone(){
        return jsonObject.optLong(PHONE);
    }
    public void setPhone(long phone){
        jsonObject.put(PHONE, phone);
    }

    public String getPermission(){
        return jsonObject.optString(PERMISSION);
    }
    public void setPermission(String permission){
        jsonObject.put(PERMISSION, permission);
    }

    public String getRole(){
        return jsonObject.optString(ROLE);
    }
    public void setRole(String role){
        jsonObject.put(ROLE, role);
    }

    public Unit getUnit() {
        return (Unit) jsonObject.optJSONObject(UNIT);
    }
    public void setUnit(Unit unit) {
        jsonObject.put(UNIT, unit);
    }

    public String getAuthenticationToken() {
        return jsonObject.optString(AUTH_TOKEN);
    }
    public void setAuthenticationToken(String authenticationToken) {
        jsonObject.put(AUTH_TOKEN, authenticationToken);
    }

    @Override
    public Set<String> validateProperty(String property) {
        return null;
    }

    @Override
    public Map<String, String> validateAll() {
        return null;
    }
}