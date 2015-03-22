package rtdc.core.model;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONObject;

import java.util.Set;

import static rtdc.core.model.DataType.*;
import static rtdc.core.model.ValidationConstraint.*;

public class User extends RtdcObject {

    public static final DataType<User> TYPE = DataType.extend(RtdcObject.TYPE, "user", User.class,
            new Field("user_id", INT),
            new Field("username", STRING, NOT_EMPTY),
            new Field("firstName", STRING, NOT_EMPTY),
            new Field("lastName", STRING, NOT_EMPTY),
            new Field("email", STRING, NOT_EMPTY, REGEX_EMAIL),
            new Field("phone", LONG),
            new Field("permission", STRING),
            new Field("role", STRING),
            new Field("unit", Unit.TYPE, NOT_NULL),
            new Field("authenticationToken", STRING));

    public User(){
    }
    public User(JSONObject jsonobject){
        super(jsonobject);
    }

    @Override
    public DataType getType() {
        return TYPE;
    }

    public String getId(){
        return (String) getProperty("user_id");
    }
    public void setId(String id){setProperty("user_id", id);}

    public String getUsername(){
        return (String) getProperty("username");
    }
    public void setUsername(String username){
        setProperty("username", username);
    }

    public String getLastName(){
        return (String) getProperty("lastName");
    }
    public void setLastName(String lastName){
        setProperty("lastName", lastName);
    }

    public String getFirstName(){
        return (String) getProperty("firstName");
    }
    public void setFirstName(String firstName){
        setProperty("firstName", firstName);
    }

    public String getEmail(){
        return (String) getProperty("email");
    }
    public void setEmail(String email){
        setProperty("email", email);
    }

    public long getPhone(){
        return (Long) getProperty("phone");
    }
    public void setPhone(long phone){
        setProperty("phone", phone);
    }

    public String getPermission(){
        return (String) getProperty("permission");
    }
    public void setPermission(String permission){
        setProperty("permission", permission);
    }

    public String getRole(){
        return (String) getProperty("role");
    }
    public void setRole(String role){
        setProperty("role", role);
    }

    public Unit getUnit() {
        return (Unit) getProperty("unit");
    }
    public void setUnit(Unit unit) {
        setProperty("unit", unit);
    }
}