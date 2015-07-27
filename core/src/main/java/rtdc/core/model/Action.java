package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;
import rtdc.core.util.Stringifier;

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
        description;
    }

    public enum Status {
        notStarted,
        inProgress,
        completed,
        failed;

        public static Stringifier<Status> getStringifier(){
            return new Stringifier<Status>() {
                @Override
                public String toString(Action.Status status) {
                    switch(status){
                        case completed: return "Completed";
                        case failed: return "Failed";
                        case inProgress: return "In progress";
                        case notStarted: return "Not started";
                        default: return status.name();
                    }
                }
            };
        }
    }

    public enum Task {
        pushForDischarge,
        offServicingTo,
        holdFor;

        public static Stringifier<Task> getStringifier(){
            return new Stringifier<Task>() {
                @Override
                public String toString(Task task) {
                    switch(task){
                        case holdFor: return "Hold";
                        case offServicingTo: return "Off servicing";
                        case pushForDischarge: return "Push for discharge";
                        default: return task.name();
                    }
                }
            };
        }
    }

    private int id;
    private Unit unit;
    private Status status; //Can never be null
    private User personResponsible;
    private String roleResponsible;
    private Task task; // this is the title
    private String target;
    private Date deadline;
    private String description;

    public Action(){}

    public Action(JSONObject object) {
        setId(object.optInt(Properties.id.name()));
        setUnit(new Unit(object.getJSONObject(Properties.unit.name())));

        status = Status.valueOf(object.optString(Properties.status.name()));

        if(object.has(Properties.personResponsible.name()))
            setPersonResponsible(new User(object.getJSONObject(Properties.personResponsible.name())));

        setRoleResponsible(object.optString(Properties.roleResponsible.name()));

        task = Task.valueOf(object.optString(Properties.task.name()));

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
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
