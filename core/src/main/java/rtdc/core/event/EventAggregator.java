package rtdc.core.event;

import com.google.common.collect.ImmutableSet;
import rtdc.core.util.WeakReferenceDecorator;

import java.util.HashSet;
import java.util.Iterator;

public class EventAggregator<T> {

    final HashSet<WeakReferenceDecorator<T>> handlers = new HashSet<WeakReferenceDecorator<T>>();

    public void addHandler(T object){
        if(object != null)
            handlers.add(new WeakReferenceDecorator<T>(object));
    }

    //Clear at the same time
    public ImmutableSet<T> getHandlers(){
        ImmutableSet.Builder<T> builder = ImmutableSet.builder();
        Iterator<WeakReferenceDecorator<T>> it = handlers.iterator();
        while(it.hasNext()){
            T next = it.next().get();
            if(next == null)
                it.remove();
            else
                builder.add(next);
        }
        return builder.build();
    }

}
