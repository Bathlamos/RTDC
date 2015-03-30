package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;

import java.util.Date;

public class Action extends RootObject{

    public enum Properties implements ObjectProperty<Action> {
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

    public enum Status {
        notStarted,
        inProgress,
        completed,
        failed
    }

    public enum Task {
        pushForDischarge,
        offServicingTo,
        holdFor
    }

    private int id;
    private Unit unit;
    private String status;
    private User personResponsible;
    private String roleResponsible;
    private String task; // this is the title
    private String target;
    private Date deadline;
    private String description;

    public Action(){}

    public Action(JSONObject object) {
        setId(object.optInt(Properties.id.name()));
        setUnit(new Unit(object.getJSONObject(Properties.unit.name())));

        String status = object.optString(Properties.status.name());
        switch (status){
            case "inProgress":
                setStatus("In Progress");
                break;
            case "completed":
                setStatus("Completed");
                break;
            case "failed":
                setStatus("Failed");
                break;
            case "notStarted":
            default:
                setStatus("Not Started");
        }

        if(object.has(Properties.personResponsible.name()))
            setPersonResponsible(new User(object.getJSONObject(Properties.personResponsible.name())));

        setRoleResponsible(object.optString(Properties.roleResponsible.name()));

        String task = object.optString(Properties.task.name());
        switch (task){
            case "pushForDischarge":
                setTask("Push for discharge");
                break;
            case "offServicingTo":
                setTask("Off Servicing");
                break;
            case "holdFor":
            default:
                setTask("Hold");
        }

        if(object.has(Properties.deadline.name()))
            setDeadline(new Date(object.getLong(Properties.deadline.name())));
        setTarget(object.optString(Properties.target.name()));
        setDescription(object.optString(Properties.description.name()));
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return "action";
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case id: return getId();
            case unit: return getUnit();
            case status: return getStatus();
            case personResponsible: return getPersonResponsible();
            case roleResponsible: return getRoleResponsible();
            case task: return getTask();
            case target: return getTarget();
            case deadline: return getDeadline();
            case description: return getDescription();
        }
        return null;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
