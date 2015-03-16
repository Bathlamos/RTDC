package rtdc.core.model;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONObject;

import java.util.Set;

import rtdc.core.model.Property.DataType;
import static rtdc.core.model.Property.DataType.*;
import static rtdc.core.model.Property.ValidationConstraint.*;

public class User extends RtdcObject {

    private static Set<Property> objectProperties;

    public static final Property ID = new Property("user_id", INT),
            USERNAME = new Property("username", STRING, NOT_EMPTY),
            FIRST_NAME = new Property("firstName", STRING, NOT_EMPTY),
            LAST_NAME = new Property("lastName", STRING, NOT_EMPTY),
            EMAIL = new Property("email", STRING, NOT_EMPTY, REGEX_EMAIL),
            PHONE = new Property("phone", LONG),
            PERMISSION = new Property("permission", STRING),
            ROLE = new Property("role", STRING),
            UNIT = new Property("unit", DataType.UNIT, NOT_NULL),
            AUTH_TOKEN = new Property("authenticationToken", STRING);

    static{
        objectProperties = Sets.newHashSet(ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PHONE, PERMISSION, ROLE, UNIT, AUTH_TOKEN);
    }

    public User(){
        super(objectProperties);
    }

    public User(JSONObject jsonobject){
        super(objectProperties, jsonobject);
    }

    public String getId(){
        return (String) getProperty(ID);
    }
    public void setId(String id){setProperty(ID, id);}

    public String getUsername(){
        return (String) getProperty(USERNAME);
    }
    public void setUsername(String username){
        setProperty(USERNAME, username);
    }

    public String getLastName(){
        return (String) getProperty(LAST_NAME);
    }
    public void setLastName(String lastName){
        setProperty(LAST_NAME, lastName);
    }

    public String getFirstName(){
        return (String) getProperty(FIRST_NAME);
    }
    public void setFirstName(String firstName){
        setProperty(FIRST_NAME, firstName);
    }

    public String getEmail(){
        return (String) getProperty(EMAIL);
    }
    public void setEmail(String email){
        setProperty(EMAIL, email);
    }

    public long getPhone(){
        return (Long) getProperty(PHONE);
    }
    public void setPhone(long phone){
        setProperty(PHONE, phone);
    }

    public String getPermission(){
        return (String) getProperty(PERMISSION);
    }
    public void setPermission(String permission){
        setProperty(PERMISSION, permission);
    }

    public String getRole(){
        return (String) getProperty(ROLE);
    }
    public void setRole(String role){
        setProperty(ROLE, role);
    }

    public Unit getUnit() {
        return (Unit) getProperty(UNIT);
    }
    public void setUnit(Unit unit) {
        setProperty(UNIT, unit);
    }
}