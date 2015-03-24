package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Comparator;

@Entity
@Table(name = "actions")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Action extends JSONObject {

    public static final String ID = "actionId",
            UNIT_ID = "unitId",
            STATUS = "status",
            ROLE = "role",
            ACTION = "action",
            TARGET = "target",
            DEADLINE = "deadline",
            NOTES = "notes";

    public Action(){}

    public Action(String json) throws JSONException{
        super(json);
    }

    @Id
    @GeneratedValue
    @Column(name = ID)
    public int getId() { return optInt(ID); }
    public void setId(int id){
        put(ID, id);
    }

    @Min(value=0)
    @Column(name = UNIT_ID)
    public int getUnitId(){
        return optInt(UNIT_ID);
    }
    public void setUnitId(int unitId){
        put(UNIT_ID, unitId);
    }

    @Size(min=1)
    @Column(name = STATUS)
    public String getStatus(){
        return optString(STATUS);
    }
    public void setStatus(String status){
        put(STATUS, status);
    }

    @Size(min=1)
    @Column(name = ROLE)
    public String getRole(){
        return optString(ROLE);
    }
    public void setRole(String role){
        put(ROLE, role);
    }

    @Size(min=1)
    @Column(name = ACTION)
    public String getAction(){
        return optString(ACTION);
    }
    public void setAction(String action){
        put(ACTION, action);
    }

    @Size(min=1)
    @Column(name = TARGET)
    public String getTarget(){
        return optString(TARGET);
    }
    public void setTarget(String target){
        put(TARGET, target);
    }

    @Size(min=1)
    @Column(name = DEADLINE)
    public String getDeadline(){
        return optString(DEADLINE);
    }
    public void setDeadline(String deadline){
        put(DEADLINE, deadline);
    }

    @Size(min=1)
    @Column(name = NOTES)
    public String getNotes(){ return optString(NOTES); }
    public void setNotes(String notes){
        put(NOTES, notes);
    }

    // Comparators for Action Plan row sorting
    public static Comparator<Action> statusComparator = new Comparator<Action>() {

        public int compare(Action action1, Action action2) {
            String value1 = action1.getStatus();
            String value2 = action2.getStatus();

            return value1.compareTo(value2);
        }

    };

    public static Comparator<Action> roleComparator = new Comparator<Action>() {

        public int compare(Action action1, Action action2) {
            String value1 = action1.getRole();
            String value2 = action2.getRole();

            return value1.compareTo(value2);
        }

    };

    public static Comparator<Action> actionComparator = new Comparator<Action>() {

        public int compare(Action action1, Action action2) {
            String value1 = action1.getAction();
            String value2 = action2.getAction();

            return value1.compareTo(value2);
        }

    };

    public static Comparator<Action> targetComparator = new Comparator<Action>() {

        public int compare(Action action1, Action action2) {
            String value1 = action1.getTarget();
            String value2 = action2.getTarget();

            return value1.compareTo(value2);
        }

    };

    public static Comparator<Action> deadlineComparator = new Comparator<Action>() {

        public int compare(Action action1, Action action2) {
            String value1 = action1.getDeadline();
            String value2 = action2.getDeadline();

            return value1.compareTo(value2);
        }

    };

}
