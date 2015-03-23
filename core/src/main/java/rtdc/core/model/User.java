package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;

public class User extends RootObject implements ValidationEnabled<User.Properties> {

    public enum Properties{
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

    private String id;
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
        setId(object.optString(Properties.id.name(), null));
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
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.id.name(), getId());
        object.put(Properties.username.name(), getUsername());
        object.put(Properties.firstName.name(), getFirstName());
        object.put(Properties.lastName.name(), getLastName());
        object.put(Properties.email.name(), getEmail());
        object.put(Properties.permission.name(), getPermission());
        object.put(Properties.role.name(), getRole());
        object.put(Properties.phone.name(), getPhone());
        object.put(Properties.unit.name(), getUnit());
    }

    @Override
    public String getType() {
        return "user";
    }

    @Override
    public boolean validate(Properties property) throws ValidationException {
        SimpleValidator validator = new SimpleValidator();
        switch(property){
            case username: return validator.expectNotEmpty(getUsername());
            case email: return validator.expectEmail(getEmail());
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

}