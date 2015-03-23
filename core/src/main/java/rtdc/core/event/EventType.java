package rtdc.core.event;

public class EventType<T extends EventHandler> {

    private final String name;

    public EventType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
