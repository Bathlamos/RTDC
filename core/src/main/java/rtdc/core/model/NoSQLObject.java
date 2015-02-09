package rtdc.core.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public abstract class NoSQLObject {

    protected Map<String, Object> properties = new HashMap<String, Object>();

    public NoSQLObject(){}

    public NoSQLObject(@Nonnull Map<String, Object> properties){
        Object type = properties.get("type");
        if(propertiesTypeValid(properties))
            //We are given an object of the same type
            this.properties.putAll(properties);
        else if(type == null){
            //Copy the essential properties
            this.properties.put("_id", properties.get("_id"));
            if(properties.containsKey("_rev"))
                this.properties.put("_rev", properties.get("_rev"));
            if(properties.containsKey("_attachments"))
                this.properties.put("_attachments", properties.get("_attachments"));
            if(properties.containsKey("_deleted"))
                this.properties.put("_deleted", properties.get("_deleted"));
            this.properties.put("type", getType());
        }
    }

    @Nullable
    public String getId(){
        return (String) properties.get("_id");
    }

    @Nullable
    public String getRevision(){
        return (String) properties.get("_rev");
    }

    public void updateProperties(@Nonnull Map<String, Object> properties){
        if(propertiesTypeValid(properties))
            //We are given an object of the same type
            this.properties.putAll(properties);
        else
            throw new RuntimeException(String.format("Properties given to object %s are of type %s.", getType(), properties.get("type")));
    }

    @Nonnull
    public abstract String getType();

    @Nonnull
    public Map<String, Object> getProperties(){
        return properties;
    }

    protected boolean propertiesTypeValid(Map<String, Object> properties){
        Object type = properties.get("type");
        return type instanceof String && ((String) type).equals(getType());
    }

}
