/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
            case name: return validator.isNotEmpty(name);
            case totalBeds: return validator.isPositiveNumber(admitsByDeadline);
            case availableBeds: return validator.isPositiveNumber(availableBeds);
            case potentialDc: return validator.isPositiveNumber(potentialDc);
            case dcByDeadline: return validator.isPositiveNumber(dcByDeadline);
            case totalAdmits: return validator.isPositiveNumber(totalAdmits);
            case admitsByDeadline: return validator.isPositiveNumber(admitsByDeadline);
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
