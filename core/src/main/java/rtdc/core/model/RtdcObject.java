package rtdc.core.model;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import java.util.*;

public abstract class RtdcObject {

    public static enum DataType {BOOLEAN, INT, LONG, STRING, JSON_ARRAY, JSON_OBJECT, UNIT, USER}
    public static enum ValidationConstraints {NOT_NULL, NOT_EMPTY, REGEX_EMAIL, POSITIVE_NUMBER}

    public static final class Property{
        private String propertyName;
        private DataType dataType;
        private ValidationConstraints[] validationConstraints;

        public Property(String propertyName, DataType dataType, ValidationConstraints... validationConstraints){
            this.setPropertyName(propertyName);
            this.setDataType(dataType);
            this.validationConstraints = validationConstraints;
        }

        @Override
        public boolean equals(Object obj) {
            return !(obj instanceof  Property) || getPropertyName().equals(((Property) obj).getPropertyName());
        }

        @Override
        public int hashCode() {
            return getPropertyName().hashCode();
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public DataType getDataType() {
            return dataType;
        }

        public void setDataType(DataType dataType) {
            this.dataType = dataType;
        }
    }

    private final Map<String, Property> objectProperties;

    private final JSONObject jsonObject;

    public RtdcObject(Set<Property> objectProperties){
        this(objectProperties, new JSONObject());
    }

    public RtdcObject(Set<Property> objectProperties, JSONObject jsonObject){
        this.jsonObject = jsonObject;
        this.objectProperties = new HashMap<String, Property>();
        for(Property property: objectProperties) {
            this.objectProperties.put(property.getPropertyName(), property);

            //Check for special properties
            switch(property.getDataType()){
                case UNIT:
                    if(getProperty(property) instanceof JSONObject)
                        setProperty(property, new Unit((JSONObject) getProperty(property)));
                    break;
                case USER:
                    if(getProperty(property) instanceof JSONObject)
                        setProperty(property, new User((JSONObject) getProperty(property)));
                    break;
            }
        }


        //Check that all properties are valid and satisfy the validation constraints
        JSONArray names = jsonObject.names();
        if(names != null)
            for(int i = 0; i < names.length(); i++){
                String propertyName = names.getString(i);
                Property property = this.objectProperties.get(propertyName);
                if(property == null)
                    throw new JSONException("Property " + propertyName + " does not belong to object " + jsonObject.toString() + ".");
                else if(!validateType(property))
                    throw new JSONException("Property " + propertyName + " of object " + jsonObject.toString() +
                            " expected to be: " + property.getDataType().toString() + ".");
            }
    }

    public Multimap<Property, String> getConstraintsViolations(){
        Multimap<Property, String> violations = HashMultimap.create();
        for(Property property: objectProperties.values())
            violations.putAll(property, validateProperty(property));
        return violations;
    }

    public Set<String> validateProperty(Property property){
        Set<String> constraintViolations = new HashSet<String>();
        String propertyName = property.getPropertyName();
        if(property.validationConstraints != null)
            for(ValidationConstraints constraint: property.validationConstraints){
                switch(constraint){
                    case NOT_EMPTY:
                        if(jsonObject.optString(propertyName).isEmpty())
                            constraintViolations.add("Cannot be empty");
                        break;
                    case NOT_NULL:
                        if(jsonObject.opt(propertyName) == null)
                            constraintViolations.add("Cannot be empty");
                        break;
                    case REGEX_EMAIL:
                        if(jsonObject.optString(propertyName).matches("^\\S+@\\S+\\.\\S+$"));
                            constraintViolations.add("Invalid email");
                        break;
                    case POSITIVE_NUMBER:
                        if(jsonObject.optLong(propertyName) < 0);
                            constraintViolations.add("The number needs to be greater than zero");
                        break;
                }
            }
        return constraintViolations;
    }

    public boolean validateType(Property property){
        String propertyName = property.getPropertyName();
        try {
            switch (property.getDataType()) {
                case BOOLEAN: return assertBoolean(propertyName);
                case INT: return assertInt(propertyName);
                case LONG: return assertLong(propertyName);
                case STRING: return assertString(propertyName);
                case JSON_ARRAY: return assertJsonArray(propertyName);
                case JSON_OBJECT: return assertJsonObject(propertyName);
                case UNIT: return jsonObject.opt(propertyName) instanceof Unit;
                case USER: return jsonObject.opt(propertyName) instanceof User;
                default: return false;
            }
        }catch(JSONException e){
            return false;
        }
    }

    protected Object getProperty(Property property){
        return jsonObject.opt(property.getPropertyName());
    }

    protected boolean hasProperty(Property property){
        return jsonObject.has(property.getPropertyName());
    }

    protected void setProperty(Property property, Object value){
        jsonObject.put(property.getPropertyName(), value);
    }


    private boolean assertBoolean(String propertyName) throws JSONException{
        jsonObject.getBoolean(propertyName);
        return true;
    }

    private boolean assertInt(String propertyName) throws JSONException{
        jsonObject.getInt(propertyName);
        return true;
    }

    private boolean assertLong(String propertyName) throws JSONException{
        jsonObject.getLong(propertyName);
        return true;
    }

    private boolean assertString(String propertyName) throws JSONException{
        jsonObject.getString(propertyName);
        return true;
    }

    private boolean assertJsonArray(String propertyName) throws JSONException{
        jsonObject.getJSONArray(propertyName);
        return true;
    }

    private boolean assertJsonObject(String propertyName) throws JSONException{
        jsonObject.getJSONObject(propertyName);
        return true;
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

}
