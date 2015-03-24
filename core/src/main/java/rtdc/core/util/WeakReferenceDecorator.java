package rtdc.core.util;

import java.lang.ref.WeakReference;

public class WeakReferenceDecorator<T>  {

    private final WeakReference<T> reference;

    public WeakReferenceDecorator(T value){
        reference = new WeakReference<T>(value);
    }

    public T get(){
        return reference.get();
    }
}
