package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;

public class Unit extends RootObject implements ValidationEnabled<Unit.Properties> {

    public enum Properties{
        id,
        name,
        totalBeds,
        availableBeds,
        potentialDc,
        dcByDeadline,
        totalAdmits,
        admitsByDeadline
    }

    private String id;
    private String name;
    private int totalBeds;
    private int availableBeds;
    private int potentialDc;
    private int dcByDeadline;
    private int totalAdmits;
    private int admitsByDeadline;

    public Unit(){}

    public Unit (JSONObject object){
        setId(object.optString(Properties.id.name(), null));
        setName(object.optString(Properties.name.name()));
        setTotalBeds(object.optInt(Properties.totalBeds.name()));
        setAvailableBeds(object.optInt(Properties.availableBeds.name()));
        setPotentialDc(object.optInt(Properties.potentialDc.name()));
        setDcByDeadline(object.optInt(Properties.dcByDeadline.name()));
        setTotalAdmits(object.optInt(Properties.totalAdmits.name()));
        setAdmitsByDeadline(object.optInt(Properties.admitsByDeadline.name()));
    }

    @Override
    public void augmentJsonObject(JSONObject object) {
        object.put(Properties.id.name(), getId());
        object.put(Properties.name.name(), getName());
        object.put(Properties.totalBeds.name(), getTotalBeds());
        object.put(Properties.availableBeds.name(), getAvailableBeds());
        object.put(Properties.potentialDc.name(), getPotentialDc());
        object.put(Properties.dcByDeadline.name(), getDcByDeadline());
        object.put(Properties.totalAdmits.name(), getTotalAdmits());
        object.put(Properties.admitsByDeadline.name(), getAdmitsByDeadline());
    }

    @Override
    public String getType() {
        return "unit";
    }

    public boolean validate(Properties property) throws ValidationException{
        SimpleValidator validator = new SimpleValidator();
        switch(property){
            case name: return validator.expectNotEmpty(name);
            case totalBeds: return validator.expectPositiveNumber(admitsByDeadline);
            case availableBeds: return validator.expectPositiveNumber(availableBeds);
            case potentialDc: return validator.expectPositiveNumber(potentialDc);
            case dcByDeadline: return validator.expectPositiveNumber(dcByDeadline);
            case totalAdmits: return validator.expectPositiveNumber(totalAdmits);
            case admitsByDeadline: return validator.expectPositiveNumber(admitsByDeadline);
        }
        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(int totalBeds) {
        this.totalBeds = totalBeds;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public int getPotentialDc() {
        return potentialDc;
    }

    public void setPotentialDc(int potentialDc) {
        this.potentialDc = potentialDc;
    }

    public int getDcByDeadline() {
        return dcByDeadline;
    }

    public void setDcByDeadline(int dcByDeadline) {
        this.dcByDeadline = dcByDeadline;
    }

    public int getTotalAdmits() {
        return totalAdmits;
    }

    public void setTotalAdmits(int totalAdmits) {
        this.totalAdmits = totalAdmits;
    }

    public int getAdmitsByDeadline() {
        return admitsByDeadline;
    }

    public void setAdmitsByDeadline(int admitsByDeadline) {
        this.admitsByDeadline = admitsByDeadline;
    }
}
