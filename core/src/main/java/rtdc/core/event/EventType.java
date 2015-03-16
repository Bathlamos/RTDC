package rtdc.core.event;

public final class EventType<T extends EventHandler> {

    private String value;

    private EventType(String value){
        this.value = value;
    }

    static <T extends EventHandler> EventType<T> build(String value){
        return new EventType<T>(value);
    }

    @Override
    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    @Override
    public String toString() {
        return value;
    }
}
