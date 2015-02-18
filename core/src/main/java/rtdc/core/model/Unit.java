package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
@Table(name = "units")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Unit extends JSONObject {

    public static final String ID = "unit_id",
            NAME = "name",
            AVAILABLE_BEDS = "availableBed",
            POTENTIAL_DC = "potentialDc",
            TOTAL_ADMITS = "totalAdmits",
            DC_BY_DEADLINE = "dcByDeadline",
            ADMITS_BY_DEADLINE = "admitsByDeadline";

    public Unit(){}

    public Unit(String json) throws JSONException{
        super(json);
    }

    @Id
    @GeneratedValue
    @Column(name = ID)
    public int getId(){
        return optInt(ID);
    }
    public void setId(int id){
        put(ID, id);
    }

    @Column(name = NAME)
    public String getName(){
        return optString(NAME);
    }
    public void setName(String name){
        put(NAME, name);
    }

    @Column(name = AVAILABLE_BEDS)
    public int getAvailableBeds(){
        return optInt(AVAILABLE_BEDS);
    }
    public void setAvailableBeds(int availableBed){
        put(AVAILABLE_BEDS, availableBed);
    }

    @Column(name = POTENTIAL_DC)
    public int getPotentialDc(){
        return optInt(POTENTIAL_DC);
    }
    public void setPotentialDc(int potentialDc){
        put(POTENTIAL_DC, potentialDc);
    }

    @Column(name = DC_BY_DEADLINE)
    public int getDcByDeadline(){
        return optInt(DC_BY_DEADLINE);
    }
    public void setDcByDeadline(int dcByDeadline){
        put(DC_BY_DEADLINE, dcByDeadline);
    }

    @Column(name = TOTAL_ADMITS)
    public int getTotalAdmits(){
        return optInt(TOTAL_ADMITS);
    }
    public void setTotalAdmits(int totalAdmit){
        put(TOTAL_ADMITS, totalAdmit);
    }

    @Column(name = ADMITS_BY_DEADLINE)
    public int getAdmitsByDeadline(){
        return optInt(ADMITS_BY_DEADLINE);
    }
    public void setAdmitsByDeadline(int admitsByDeadline){
        put(ADMITS_BY_DEADLINE, admitsByDeadline);
    }

}
