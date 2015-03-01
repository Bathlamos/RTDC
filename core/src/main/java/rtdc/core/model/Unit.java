package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

public class Unit extends JSONObject {

    public static final String ID = "unit_id",
            NAME = "name",
            TOTAL_BEDS = "totalBeds",
            AVAILABLE_BEDS = "availableBeds",
            POTENTIAL_DC = "potentialDc",
            TOTAL_ADMITS = "totalAdmits",
            DC_BY_DEADLINE = "dcByDeadline",
            ADMITS_BY_DEADLINE = "admitsByDeadline";

    public Unit(){}

    public Unit(String json) throws JSONException{
        super(json);
    }

    public int getId(){
        return optInt(ID);
    }
    public void setId(int id){
        put(ID, id);
    }

    public String getName(){
        return optString(NAME);
    }
    public void setName(String name){
        put(NAME, name);
    }

    public int getTotalBeds(){
        return optInt(TOTAL_BEDS);
    }
    public void setTotalBeds(int totalBeds){
        put(TOTAL_BEDS, totalBeds);
    }

    public int getAvailableBeds(){
        return optInt(AVAILABLE_BEDS);
    }
    public void setAvailableBeds(int availableBeds){
        put(AVAILABLE_BEDS, availableBeds);
    }

    public int getPotentialDc(){
        return optInt(POTENTIAL_DC);
    }
    public void setPotentialDc(int potentialDc){
        put(POTENTIAL_DC, potentialDc);
    }

    public int getDcByDeadline(){
        return optInt(DC_BY_DEADLINE);
    }
    public void setDcByDeadline(int dcByDeadline){
        put(DC_BY_DEADLINE, dcByDeadline);
    }

    public int getTotalAdmits(){
        return optInt(TOTAL_ADMITS);
    }
    public void setTotalAdmits(int totalAdmit){
        put(TOTAL_ADMITS, totalAdmit);
    }

    public int getAdmitsByDeadline(){
        return optInt(ADMITS_BY_DEADLINE);
    }
    public void setAdmitsByDeadline(int admitsByDeadline){
        put(ADMITS_BY_DEADLINE, admitsByDeadline);
    }

}
