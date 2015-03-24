package rtdc.core.util;

public class WeakReferenceDecorator<T> {

    private final T value;

    public WeakReferenceDecorator(T value){
        this.value = value;
    }

    public T get(){
        return value;
    }

}
