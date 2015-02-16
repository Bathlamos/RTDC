package rtdc.core.model;

import com.goodow.realtime.json.impl.JreJsonObject;

public class User extends JreJsonObject {

    public int getId() {
        return (int) getNumber("id");
    }
    public void setId(int id) {
        set("id", id);
    }

    public String getSurname() {
        return getString("surname");
    }
    public void setSurname(String surname) {
        set("surname", surname);
    }

    public String getFirstName() {
        return getString("firstName");
    }
    public void setFirstName(String firstName) {
        set("firstName", firstName);
    }


}
