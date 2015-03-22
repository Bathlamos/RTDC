package rtdc.core.model;

public final class ObjectType<T extends RtdcObject> {

    private String value;
    private Class<T> clazz;

    private ObjectType(String value, Class<T> clazz){
        this.value = value;
        this.clazz = clazz;
    }

    public static ObjectType build(String value, Class<? extends RtdcObject> clazz){
        return new ObjectType(value, clazz);
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    @Override
    public String toString() {
        return value;
    }

    public Class<? extends RtdcObject> getObjectClass() {
        return clazz;
    }

}
