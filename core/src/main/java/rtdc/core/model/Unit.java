package rtdc.core.model;

import com.google.common.collect.Sets;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import java.util.Set;
import static rtdc.core.model.RtdcObject.ValidationConstraints.*;

public class Unit extends RtdcObject {

    private static Set<RtdcObject.Property> objectProperties;

    public static final Property ID = new Property("unit_id", DataType.INT),
            NAME = new Property("name", DataType.STRING),
            TOTAL_BEDS = new Property("username", DataType.INT, POSITIVE_NUMBER),
            AVAILABLE_BEDS = new Property("lastName", DataType.INT, POSITIVE_NUMBER),
            POTENTIAL_DC = new Property("email", DataType.INT, POSITIVE_NUMBER),
            TOTAL_ADMITS = new Property("phone", DataType.INT, POSITIVE_NUMBER),
            DC_BY_DEADLINE = new Property("permission", DataType.INT, POSITIVE_NUMBER),
            ADMITS_BY_DEADLINE = new Property("role", DataType.INT, POSITIVE_NUMBER);

    static{
        objectProperties = Sets.newHashSet(ID, NAME, TOTAL_BEDS, AVAILABLE_BEDS, AVAILABLE_BEDS, POTENTIAL_DC,
                TOTAL_ADMITS, TOTAL_ADMITS, DC_BY_DEADLINE, ADMITS_BY_DEADLINE);
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

}
