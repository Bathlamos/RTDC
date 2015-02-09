package rtdc.core.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public class Unit extends NoSQLObject {

    public Unit(){}

    public Unit(@Nonnull Map<String, Object> properties){
        super(properties);
    }

    @Nullable
    public String getName(){
        return (String) properties.get("name");
    }

    public void setName(@Nonnull String name){
        properties.put("name", name);
    }

    @Nonnull
    @Override
    public String getType() {
        return null;
    }
}
