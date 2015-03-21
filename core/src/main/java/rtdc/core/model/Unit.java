package rtdc.core.model;

import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import javax.persistence.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Comparator;

@Entity
@Table(name = "units")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
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

    @Id
    @GeneratedValue
    @Column(name = ID)
    public int getId() { return optInt(ID); }
    public void setId(int id){
        put(ID, id);
    }

    @NotNull
    @Size(min=1)
    @Column(name = NAME)
    public String getName(){
        return optString(NAME);
    }
    public void setName(String name){
        put(NAME, name);
    }

    @Min(value=0)
    @Column(name = TOTAL_BEDS)
    public int getTotalBeds(){
        return optInt(TOTAL_BEDS);
    }
    public void setTotalBeds(int totalBeds){
        put(TOTAL_BEDS, totalBeds);
    }

    @Min(value=0)
    @Column(name = AVAILABLE_BEDS)
    public int getAvailableBeds(){
        return optInt(AVAILABLE_BEDS);
    }
    public void setAvailableBeds(int availableBeds){
        put(AVAILABLE_BEDS, availableBeds);
    }

    @Min(value=0)
    @Column(name = POTENTIAL_DC)
    public int getPotentialDc(){
        return optInt(POTENTIAL_DC);
    }
    public void setPotentialDc(int potentialDc){
        put(POTENTIAL_DC, potentialDc);
    }

    @Min(value=0)
    @Column(name = DC_BY_DEADLINE)
    public int getDcByDeadline(){
        return optInt(DC_BY_DEADLINE);
    }
    public void setDcByDeadline(int dcByDeadline){
        put(DC_BY_DEADLINE, dcByDeadline);
    }

    @Min(value=0)
    @Column(name = TOTAL_ADMITS)
    public int getTotalAdmits(){
        return optInt(TOTAL_ADMITS);
    }
    public void setTotalAdmits(int totalAdmit){
        put(TOTAL_ADMITS, totalAdmit);
    }

    @Min(value=0)
    @Column(name = ADMITS_BY_DEADLINE)
    public int getAdmitsByDeadline(){
        return optInt(ADMITS_BY_DEADLINE);
    }
    public void setAdmitsByDeadline(int admitsByDeadline){
        put(ADMITS_BY_DEADLINE, admitsByDeadline);
    }

    public static Comparator<Unit> availableBedsComparator = new Comparator<Unit>() {

        public int compare(Unit unit1, Unit unit2) {
            int availableBeds1 = unit1.getAvailableBeds();
            int availableBeds2 = unit2.getAvailableBeds();

            // Acending order
            return availableBeds1 - availableBeds2;

            // Descending order
            // return availableBeds2 - availableBeds1;
        }

    };

    public static Comparator<Unit> potentialDcComparator = new Comparator<Unit>() {

        public int compare(Unit unit1, Unit unit2) {
            int potentialDc1 = unit1.getPotentialDc();
            int potentialDc2 = unit2.getPotentialDc();

            // Acending order
            return potentialDc1 - potentialDc2;
        }

    };

    public static Comparator<Unit> dcByDeadlineComparator = new Comparator<Unit>() {

        public int compare(Unit unit1, Unit unit2) {
            int dcByDeadline1 = unit1.getDcByDeadline();
            int dcByDeadline2 = unit2.getDcByDeadline();

            // Acending order
            return dcByDeadline1 - dcByDeadline2;
        }

    };

    public static Comparator<Unit> totalAdmitsComparator = new Comparator<Unit>() {

        public int compare(Unit unit1, Unit unit2) {
            int totalAdmits1 = unit1.getTotalAdmits();
            int totalAdmits2 = unit2.getTotalAdmits();

            // Acending order
            return totalAdmits1 - totalAdmits2;
        }

    };

    public static Comparator<Unit> admitsByDeadlineComparator = new Comparator<Unit>() {

        public int compare(Unit unit1, Unit unit2) {
            int admitsByDeadline1 = unit1.getAdmitsByDeadline();
            int admitsByDeadline2 = unit2.getAdmitsByDeadline();

            // Acending order
            return admitsByDeadline1 - admitsByDeadline2;
        }
    };

    public static Comparator<Unit> statusAtDeadlineComparator = new Comparator<Unit>() {

        public int compare(Unit unit1, Unit unit2) {
            int statusAtDeadline1 = unit1.getAvailableBeds() + unit1.getDcByDeadline() - unit1.getAdmitsByDeadline();
            int statusAtDeadline2 = unit2.getAvailableBeds() + unit2.getDcByDeadline() - unit2.getAdmitsByDeadline();

            // Acending order
            return statusAtDeadline1 - statusAtDeadline2;
        }

    };
}
