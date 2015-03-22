package rtdc.core.model;

import com.google.common.collect.*;
import rtdc.core.json.JSONArray;
import rtdc.core.json.JSONException;
import rtdc.core.json.JSONObject;
import rtdc.core.model.Property.ValidationConstraint;

import java.util.*;

import static rtdc.core.model.DataType.INT;
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
                else if(!Validator.isTypeValid(fieldName.get(propertyName), jsonObject.get(propertyName)))
                    throw new JSONException("Property " + propertyName + " of object " + jsonObject.toString() +
                            " expected to be: " + fieldName.get(propertyName).getDataType().toString() + ".");
                }
            }
        }

    protected Object getProperty(String propertyName){
        return jsonObject.opt(propertyName);
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
