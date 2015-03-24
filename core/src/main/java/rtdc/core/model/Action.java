package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;

public class Action extends RootObject implements ValidationEnabled<Action.Properties> {

    public enum Properties{
        id,
        unit,
        status,
        personResponsible,
        roleResponsible,
        task,
        target,
        deadline,
        description
    }

    private int id;
    private Unit unit;
    private String status;
    private User personResponsible;
    private String roleResponsible;
    private String task;
    private String target;
    private long deadline;
    private String description;

    public Action(){}

    public Action(JSONObject object) {
        setId(object.optInt(Properties.id.name()));
        setUnit(new Unit(object.getJSONObject(Properties.unit.name())));
        setStatus(object.optString(Properties.status.name()));
        setPersonResponsible(new User(object.getJSONObject(Properties.personResponsible.name())));
        setRoleResponsible(object.optString(Properties.roleResponsible.name()));
        setTask(object.optString(Properties.task.name()));
        setTarget(object.optString(Properties.target.name()));
        setDeadline(object.optLong(Properties.deadline.name()));
        setDescription(object.optString(Properties.description.name()));
    }

    @Override
    public void augmentJsonObject(JSONObject object){
        object.put(Properties.id.name(), getId());
        object.put(Properties.unit.name(), getUnit().toJsonObject());
        object.put(Properties.status.name(), getStatus());
        object.put(Properties.personResponsible.name(), personResponsible == null? null: personResponsible.toJsonObject());
        object.put(Properties.roleResponsible.name(), roleResponsible == null? null: getRoleResponsible());
        object.put(Properties.task.name(), getTask());
        object.put(Properties.target.name(), getTarget());
        object.put(Properties.deadline.name(), getDeadline());
        object.put(Properties.description.name(), getDescription());
    }

    @Override
    public String getType() {
        return "action";
    }

    @Override
    public boolean validate(Properties property) throws ValidationException {
        SimpleValidator validator = new SimpleValidator();
        switch(property){

        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getPersonResponsible() {
        return personResponsible;
    }

    public void setPersonResponsible(User personResponsible) {
        this.personResponsible = personResponsible;
    }

    public String getRoleResponsible() {
        return roleResponsible;
    }

    public void setRoleResponsible(String roleResponsible) {
        this.roleResponsible = roleResponsible;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
