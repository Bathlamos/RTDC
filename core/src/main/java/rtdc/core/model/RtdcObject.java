package rtdc.core.model;

import com.google.common.collect.*;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;

import java.util.*;

import static rtdc.core.model.DataType.STRING;

public abstract class RtdcObject {

    public static final DataType<RtdcObject> TYPE = new DataType<RtdcObject>("object", RtdcObject.class,
            new Field("type", STRING));

    private final JSONObject jsonObject;

    public RtdcObject(){
        this(new JSONObject("{}"));
    }

    public RtdcObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;

        this.jsonObject.put("type", getType().getName());

        //Check that all properties are valid and satisfy the validation constraints
        JSONArray names = jsonObject.names();
        if(names != null) {
            ImmutableSet<Field> fields = getType().getFields();

            //Create a map with the propertyName as key
            Map<String, Field> fieldName = new HashMap<String, Field>(fields.size());
            for (Field f : fields)
                fieldName.put(f.getName(), f);

            for (int i = 0; i < names.length(); i++) {
                String propertyName = names.getString(i);
                if (!fieldName.containsKey(propertyName))
                    throw new JSONException("Property " + propertyName + " does not belong to object " + jsonObject.toString() + ".");
                else {
                    Field f = fieldName.get(propertyName);
                    if (!isTypeValid(f))
                        throw new JSONException("Property " + propertyName + " of object " + jsonObject.toString() +
                                " expected to be: " + fieldName.get(propertyName).getDataType().toString() + ".");

                    //Instantiate the non-primitive objects
                    if(!f.getDataType().isPrimitive())
                        setProperty(f.getName(), JsonDeserializer.instantiateObject(f, jsonObject.getJSONObject(f.getName())));
                }
            }
        }
    }

    protected Object getProperty(String propertyName){
        return jsonObject.opt(propertyName);
    }

    public boolean isTypeValid(Field f){
        Object o = jsonObject.get(f.getName());
        return o == null || o.getClass() == f.getDataType().getClazz();
    }

    public ImmutableSet<String> validateProperty(Field f){
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        String propertyName = f.getName();
        for(ValidationConstraint constraint: f.getValidationConstraints()) {
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
                    if (jsonObject.optLong(propertyName) <= 0) ;
                    builder.add("The number needs to be greater than zero");
                    break;
            }
        }
        return builder.build();
    }

    public ImmutableMultimap<Field, String> validateObject(){
        ImmutableMultimap.Builder<Field, String> builder = ImmutableMultimap.builder();
        for(Object f: getType().getFields()){
            ImmutableSet<String> constraintViolations = validateProperty((Field) f);
            if(constraintViolations != null)
                builder.putAll((Field) f, constraintViolations);
        }
        return builder.build();
    }

    protected void setProperty(String propertyName, Object value){
        jsonObject.put(propertyName, value);
    }

    public abstract DataType getType();

    @Override
    public String toString() {
        return jsonObject.toString();
    }

}
