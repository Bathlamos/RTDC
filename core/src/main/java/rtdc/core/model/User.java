package rtdc.core.model;

import org.hibernate.validator.constraints.Email;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
            PERMISSION = "PERMISSION",
            ROLE = "ROLE";

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
    @Column(name = USERNAME, unique = true, nullable = false)
    public String getUsername(){
        return optString(USERNAME);
    }
    public void setUsername(String username){
        put(USERNAME, username);
    }

    @NotNull
    @Column(name = SURNAME)
    public String getSurname(){
        return optString(SURNAME);
    }
    public void setSurname(String surname){
        put(SURNAME, surname);
    }

    @NotNull
    @Column(name = FIRSTNAME)
    public String getFirstName(){
        return optString(FIRSTNAME);
    }
    public void setFirstName(String firstName){
        put(FIRSTNAME, firstName);
    }

    @Email
    @Column(name = EMAIL)
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
    @Column(name = PERMISSION)
    public String getPermission(){
        return optString(PERMISSION);
    }
    public void setPermission(String permission){
        put(PERMISSION, permission);
    }

    @NotNull
    @Column(name = ROLE)
    public String getRole(){
        return optString(ROLE);
    }
    public void setRole(String role){
        put(ROLE, role);
    }

    public String getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }
}