package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import static rtdc.core.model.DataType.*;
import static rtdc.core.model.ValidationConstraint.*;

public class Unit extends RtdcObject {

    public static final DataType<Unit> TYPE = DataType.extend(RtdcObject.TYPE, "unit", Unit.class,
            new Field("unit_id", INT),
            new Field("name", STRING),
            new Field("totalBeds", INT, POSITIVE_NUMBER),
            new Field("availableBeds", INT, POSITIVE_NUMBER),
            new Field("potentialDc", INT, POSITIVE_NUMBER),
            new Field("dcByDeadline", INT, POSITIVE_NUMBER),
            new Field("totalAdmits", INT, POSITIVE_NUMBER),
            new Field("admitsByDeadline", INT, POSITIVE_NUMBER));

    public Unit(){
    }

    public Unit(JSONObject jsonObject) throws JSONException{
        super(jsonObject);
    }

    @Override
    public DataType getType() {
        return TYPE;
    }

    public String getId(){
        return (String) getProperty("unit_id");
    }
    public void setId(String id){
        setProperty("unit_id", id);
    }

    public String getName(){
        return (String) getProperty("name");
    }
    public void setName(String name){
        setProperty("name", name);
    }

    public int getTotalBeds(){
        return (Integer) getProperty("totalBeds");
    }
    public void setTotalBeds(int totalBeds){
        setProperty("totalBeds", totalBeds);
    }

    public int getAvailableBeds(){
        return (Integer) getProperty("availableBeds");
    }
    public void setAvailableBeds(int availableBeds){
        setProperty("availableBeds", availableBeds);
    }

    public int getPotentialDc(){
        return (Integer) getProperty("potentialDc");
    }
    public void setPotentialDc(int potentialDc){
        setProperty("potentialDc", potentialDc);
    }

    public int getDcByDeadline(){
        return (Integer) getProperty("dcByDeadline");
    }
    public void setDcByDeadline(int dcByDeadline){
        setProperty("dcByDeadline", dcByDeadline);
    }

    public int getTotalAdmits(){
        return (Integer) getProperty("totalAdmits");
    }
    public void setTotalAdmits(int totalAdmit){
        setProperty("totalAdmits", totalAdmit);
    }

    public int getAdmitsByDeadline(){
        return (Integer) getProperty("admitsByDeadline");
    }
    public void setAdmitsByDeadline(int admitsByDeadline){
        setProperty("admitsByDeadline", admitsByDeadline);
    }

}
