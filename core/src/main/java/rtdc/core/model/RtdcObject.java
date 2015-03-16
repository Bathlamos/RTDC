package rtdc.core.model;

import com.google.common.collect.*;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Property.ValidationConstraint;

import java.util.*;

public abstract class RtdcObject {

    private final ImmutableMap<String, Property> objectProperties;
    private final JSONObject jsonObject;

    public RtdcObject(Set<Property> objectProperties){
        this(objectProperties, new JSONObject());
    }

    public RtdcObject(Set<Property> objectProperties, JSONObject jsonObject){
        this.jsonObject = jsonObject;
        ImmutableMap.Builder<String, Property> builder = ImmutableMap.builder();
        for(Property property: objectProperties) {
            builder.put(property.getPropertyName(), property);

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

        this.objectProperties = builder.build();


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

    public ImmutableMultimap<Property, String> getConstraintsViolations(){
        ImmutableMultimap.Builder<Property, String> builder = ImmutableMultimap.builder();
        for(Property property: objectProperties.values())
            builder.putAll(property, validateProperty(property));
        return builder.build();
    }

    public ImmutableSet<String> validateProperty(Property property){
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        String propertyName = property.getPropertyName();
        for(ValidationConstraint constraint: property.getValidationConstraints()) {
            switch (constraint) {
                case NOT_EMPTY:
                    if (jsonObject.optString(propertyName).isEmpty())
                        builder.add("Cannot be empty");
                    break;
                case NOT_NULL:
                    if (jsonObject.opt(propertyName) == null)
                        builder.add("Cannot be empty");
                    break;
                case REGEX_EMAIL:
                    if (jsonObject.optString(propertyName).matches("^\\S+@\\S+\\.\\S+$")) ;
                    builder.add("Invalid email");
                    break;
                case POSITIVE_NUMBER:
                    if (jsonObject.optLong(propertyName) < 0) ;
                    builder.add("The number needs to be greater than zero");
                    break;
            }
        }
        return builder.build();
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
