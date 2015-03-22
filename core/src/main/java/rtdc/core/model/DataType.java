package rtdc.core.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class DataType<T> {

    public static final DataType<Boolean> BOOLEAN = new DataType<Boolean>(true, "boolean", boolean.class);
    public static final DataType<Integer> INT = new DataType<Integer>(true, "int", int.class);
    public static final DataType<Long> LONG = new DataType<Long>(true, "long", long.class);
    public static final DataType<String> STRING = new DataType<String>(true, "string", String.class);

    private final boolean isPrimitive;
    private final String name;
    private final Class<T> clazz;
    private final ImmutableSet<Field> fields;

    DataType(String name, Class<T> clazz, Field... fields){
        this(false, name, clazz, fields);
    }

    DataType(boolean isPrimitive, String name, Class<T> clazz, Field... fields){
        this.isPrimitive = isPrimitive;
        this.name = name;
        this.clazz = clazz;
        if(fields != null)
            this.fields = ImmutableSet.copyOf(fields);
        else
            this.fields = ImmutableSet.of();
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public String getName() {
        return name;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public ImmutableSet<Field> getFields(){
        return fields;
    }

    public static <P, C extends P> DataType<C> extend(DataType<P> parentDataType, String name, Class<C> clazz, Field... field){
        Set<Field> set = Sets.newHashSet(parentDataType.fields);
        if(field != null)
            for(Field f: field)
                set.add(f);
        return new DataType<C>(name, clazz, set.toArray(new Field[set.size()]));
    }
}
