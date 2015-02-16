package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="OBJ_TYPE",
        discriminatorType=DiscriminatorType.STRING,
        length=1
)
@DiscriminatorValue("u")
public class User extends JSONObject implements Serializable{

    public static final String ID = "id",
        USERNAME = "username",
        SURNAME = "surname",
        FIRSTNAME = "firstname";

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

    @Column(name = USERNAME, unique = true)
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

}