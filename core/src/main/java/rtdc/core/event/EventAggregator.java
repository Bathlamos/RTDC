package rtdc.core.event;

import com.google.common.collect.ImmutableSet;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

public class EventAggregator<T> {

    final HashSet<WeakReference<T>> handlers = new HashSet<WeakReference<T>>();

    public void addHandler(T object){
        if(object != null)
            handlers.add(new WeakReference<T>(object));
    }

    //Clear at the same time
    public ImmutableSet<T> getHandlers(){
        ImmutableSet.Builder<T> builder = ImmutableSet.builder();
        Iterator<WeakReference<T>> it = handlers.iterator();
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
