package rtdc.core.model;

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
        FIRSTNAME = "firstname",
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

    @Column(name = SURNAME)
    public String getSurname(){
        return optString(SURNAME);
    }
    public void setSurname(String surname){
        put(SURNAME, surname);
    }

    @Column(name = FIRSTNAME)
    public String getFirstName(){
        return optString(FIRSTNAME);
    }
    public void setFirstName(String firstName){
        put(FIRSTNAME, firstName);
    }

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