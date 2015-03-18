package rtdc.core.model;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import java.util.Set;
import static rtdc.core.model.Property.DataType.*;
import static rtdc.core.model.Property.ValidationConstraint.*;

public class Unit extends RtdcObject {

    private static Set<Property> objectProperties;

    public static final Property ID = new Property("unit_id", INT),
            NAME = new Property("name", STRING),
            TOTAL_BEDS = new Property("username", STRING, POSITIVE_NUMBER),
            AVAILABLE_BEDS = new Property("lastName", STRING, POSITIVE_NUMBER),
            POTENTIAL_DC = new Property("email", STRING, POSITIVE_NUMBER),
            TOTAL_ADMITS = new Property("phone", STRING, POSITIVE_NUMBER),
            DC_BY_DEADLINE = new Property("permission", STRING, POSITIVE_NUMBER),
            ADMITS_BY_DEADLINE = new Property("role", STRING, POSITIVE_NUMBER);

    private String id, name;
    private int totalBeds, availableBeds, potentialDc, totalAdmits, dcByDeadline, admitsByDeadline;

    static{
        objectProperties = Sets.newHashSet(ID, NAME, TOTAL_BEDS, AVAILABLE_BEDS, AVAILABLE_BEDS, POTENTIAL_DC,
                TOTAL_ADMITS, DC_BY_DEADLINE, ADMITS_BY_DEADLINE);
    }

    public Unit(){
        super(objectProperties);
    }

    public Unit(JSONObject jsonObject) throws JSONException{
        super(objectProperties, jsonObject);
    }

    public String getId(){
        return (String) getProperty(ID);
    }
    public void setId(String id){
        setProperty(ID, id);
    }

    public String getName(){
        return (String) getProperty(NAME);
    }
    public void setName(String name){
        setProperty(NAME, name);
    }

    public int getTotalBeds(){
        return (Integer) getProperty(TOTAL_BEDS);
    }
    public void setTotalBeds(int totalBeds){
        setProperty(TOTAL_BEDS, totalBeds);
    }

    public int getAvailableBeds(){
        return (Integer) getProperty(AVAILABLE_BEDS);
    }
    public void setAvailableBeds(int availableBeds){
        setProperty(AVAILABLE_BEDS, availableBeds);
    }

    public int getPotentialDc(){
        return (Integer) getProperty(POTENTIAL_DC);
    }
    public void setPotentialDc(int potentialDc){
        setProperty(POTENTIAL_DC, potentialDc);
    }

    public int getDcByDeadline(){
        return (Integer) getProperty(DC_BY_DEADLINE);
    }
    public void setDcByDeadline(int dcByDeadline){
        setProperty(DC_BY_DEADLINE, dcByDeadline);
    }

    public int getTotalAdmits(){
        return (Integer) getProperty(TOTAL_ADMITS);
    }
    public void setTotalAdmits(int totalAdmit){
        setProperty(TOTAL_ADMITS, totalAdmit);
    }

    public int getAdmitsByDeadline(){
        return (Integer) getProperty(ADMITS_BY_DEADLINE);
    }
    public void setAdmitsByDeadline(int admitsByDeadline){
        setProperty(ADMITS_BY_DEADLINE, admitsByDeadline);
    }

    public static void fromJson(JSONObject object){
        id = object.optString("id");
    }

    public JSONObject toJson(){
        JSONObject object = new JSONObject();
        object.put("id", id);
        return object;
    }

    public void validate()

}
