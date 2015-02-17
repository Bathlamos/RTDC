package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

public class Unit extends JSONObject {

    public Unit(){}

    public Unit(String json) throws JSONException{
        super(json);
    }

    public int getId(){
        return optInt("id");
    }
    public void setId(int id){
        put("id", id);
    }

    public String getName(){
        return optString("name");
    }
    public void setName(String name){
        put("name", name);
    }

    public int getAvailableBeds(){
        return optInt("availableBed");
    }
    public void setAvailableBeds(int availableBed){
        put("availableBed", availableBed);
    }

    public int getPotentialDc(){
        return optInt("potentialDc");
    }
    public void setPotentialDc(int potentialDc){
        put("potentialDc", potentialDc);
    }

    public int getDcByDeadline(){
        return optInt("dcByDeadline");
    }
    public void setDcByDeadline(int dcByDeadline){
        put("dcByDeadline", dcByDeadline);
    }

    public int getTotalAdmits(){
        return optInt("totalAdmit");
    }
    public void setTotalAdmits(int totalAdmit){
        put("totalAdmit", totalAdmit);
    }

    public int getAdmitsByDeadline(){
        return optInt("admitsByDeadline");
    }
    public void setAdmitsByDeadline(int admitsByDeadline){
        put("admitsByDeadline", admitsByDeadline);
    }

}
