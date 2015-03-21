package rtdc.core.model;

import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class User extends JSONObject implements Serializable{

    public static final String ID = "user_id",
            USERNAME = "username",
            SURNAME = "surname",
            EMAIL = "email",
            PHONE = "phone",
            FIRSTNAME = "firstname",
            PERMISSION = "permission",
            ROLE = "role",
            UNIT = "unit",
            AUTH_TOKEN = "authenticationToken";

    private String authenticationToken;

    public User(){}

    public User(String json) throws JSONException{
        super(json);
    }

    @Id
    @GeneratedValue
    @Column(name = ID)
    public int getId(){
        return optInt(ID);
    }
    public void setId(int id){put(ID, id);}

    @NotNull
    @Size(min=1)
    @Column(name = USERNAME, unique = true, nullable = false)
    public String getUsername(){
        return optString(USERNAME);
    }
    public void setUsername(String username){
        put(USERNAME, username);
    }

    @NotNull
    @Size(min=1)
    @Column(name = SURNAME)
    public String getSurname(){
        return optString(SURNAME);
    }
    public void setSurname(String surname){
        put(SURNAME, surname);
    }

    @NotNull
    @Size(min=1)
    @Column(name = FIRSTNAME)
    public String getFirstName(){
        return optString(FIRSTNAME);
    }
    public void setFirstName(String firstName){
        put(FIRSTNAME, firstName);
    }

    @Column(name = EMAIL)
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    public String getEmail(){
        return optString(EMAIL);
    }
    public void setEmail(String email){
        put(EMAIL, email);
    }

    @Column(name = PHONE)
    public long getPhone(){
        return optLong(PHONE);
    }
    public void setPhone(long phone){
        put(PHONE, phone);
    }

    @NotNull
    @Size(min=1)
    @Column(name = PERMISSION)
    public String getPermission(){
        return optString(PERMISSION);
    }
    public void setPermission(String permission){
        put(PERMISSION, permission);
    }

    @NotNull
    @Size(min=1)
    @Column(name = ROLE)
    public String getRole(){
        return optString(ROLE);
    }
    public void setRole(String role){
        put(ROLE, role);
    }

    @ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = Unit.class)
    public Unit getUnit() {
        return (Unit) optJSONObject(UNIT);
    }
    public void setUnit(Unit unit) {
        put(UNIT, unit);
    }

    @Transient
    public String getAuthenticationToken() {
        return optString(AUTH_TOKEN);
    }
    public void setAuthenticationToken(String authenticationToken) {
        put(AUTH_TOKEN, authenticationToken);
    }
}