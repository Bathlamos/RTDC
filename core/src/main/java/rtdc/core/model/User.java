package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;

public class User extends RootObject {

    public enum Properties implements ObjectProperty<User> {
        id,
        username,
        firstName,
        lastName,
        email,
        permission,
        role,
        phone,
        unit
    }

    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String permission;
    private String role;
    private long phone;
    private Unit unit;

    public User(){}

    public User (JSONObject object){
        setId(object.optInt(Properties.id.name()));
        setUsername(object.optString(Properties.username.name()));
        setFirstName(object.optString(Properties.firstName.name()));
        setLastName(object.optString(Properties.lastName.name()));
        setEmail(object.optString(Properties.email.name()));
        setPermission(object.optString(Properties.permission.name()));
        setRole(object.optString(Properties.role.name()));
        setPhone(object.optLong(Properties.phone.name()));
        if(object.has(Properties.unit.name()))
            setUnit(new Unit(object.getJSONObject(Properties.unit.name())));
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return "user";
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case id: return id;
            case username: return username;
            case firstName: return firstName;
            case lastName: return lastName;
            case email: return email;
            case permission: return permission;
            case role: return role;
            case phone: return phone;
            case unit: return unit;
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public float[] getProfileColor(){
        byte [] bytes = (getFirstName() + " " + getLastName()).getBytes();
        float hue = 0;
        for (int i = 0; i < bytes.length; i++)
        {
            hue += ((long) bytes[i] & 0xffL) << (8 * i);
            hue %= 360;
        }

        float saturation = 0.8f;
        float brightness = 0.4f; //Also known as value

        return new float[]{hue, saturation,brightness};
    }

}