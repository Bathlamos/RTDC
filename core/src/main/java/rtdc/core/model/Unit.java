package rtdc.core.model;

import rtdc.core.exception.ValidationException;
import rtdc.core.json.JSONObject;

public class Unit extends RootObject implements Comparable<Unit>{

    public enum Properties implements ObjectProperty<Unit> {
        id,
        name,
        totalBeds,
        availableBeds,
        potentialDc,
        dcByDeadline,
        totalAdmits,
        admitsByDeadline,
        statusAtDeadline
    }

    private int id;
    private String name;
    private int totalBeds;
    private int availableBeds;
    private int potentialDc;
    private int dcByDeadline;
    private int totalAdmits;
    private int admitsByDeadline;

    public Unit(){}

    public Unit (JSONObject object){
        setId(object.optInt(Properties.id.name()));
        setName(object.optString(Properties.name.name()));
        setTotalBeds(object.optInt(Properties.totalBeds.name()));
        setAvailableBeds(object.optInt(Properties.availableBeds.name()));
        setPotentialDc(object.optInt(Properties.potentialDc.name()));
        setDcByDeadline(object.optInt(Properties.dcByDeadline.name()));
        setTotalAdmits(object.optInt(Properties.totalAdmits.name()));
        setAdmitsByDeadline(object.optInt(Properties.admitsByDeadline.name()));
    }

    @Override
    public ObjectProperty[] getProperties() {
        return Properties.values();
    }

    @Override
    public String getType() {
        return "unit";
    }

    @Override
    public Object getValue(ObjectProperty property) {
        switch((Properties) property){
            case id : return id;
            case name: return name;
            case totalBeds: return totalBeds;
            case availableBeds: return availableBeds;
            case potentialDc: return potentialDc;
            case dcByDeadline: return dcByDeadline;
            case totalAdmits: return totalAdmits;
            case admitsByDeadline: return admitsByDeadline;
            case statusAtDeadline: return getStatusAtDeadline();
        }
        return null;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Unit other = (Unit) obj;
        if(id != other.id)
            return false;
        if((name == null) ? (other.name != null) : !name.equals(other.name))
            return false;
        if(totalBeds != other.totalBeds)
             return false;
        if(availableBeds != other.availableBeds)
             return false;
        if(potentialDc != other.potentialDc)
             return false;
        if(dcByDeadline != other.dcByDeadline)
             return false;
        if(totalAdmits != other.totalAdmits)
             return false;
        if(admitsByDeadline != other.admitsByDeadline)
             return false;

        return true;
    }

    @Override
    public int compareTo(Unit other) {
        if(name == null && other.name == null)
            return 0;
        else if(name != null)
            return name.compareToIgnoreCase(other.name);
        else
            return other.name.compareToIgnoreCase(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getStatusAtDeadline(){
        return getAvailableBeds() + getDcByDeadline() - getAdmitsByDeadline();
    }
}
