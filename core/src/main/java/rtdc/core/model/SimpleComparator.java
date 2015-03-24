package rtdc.core.model;

import java.util.Comparator;

public class SimpleComparator {

    public static <T extends RootObject> Comparator<T> forProperty(final ObjectProperty<T> property){
        return new Comparator<T>() {
            @Override
            public int compare(T a, T b) {
                if(a == b)
                    return 0;
                else if(a == null)
                    return -1;
                else if(b == null)
                    return 1;
                else{
                    Object valueA = a.getValue(property);
                    Object valueB = a.getValue(property);
                    if(a == b)
                        return 0;
                    else if(a == null)
                        return -1;
                    else if(b == null)
                        return 1;
                    else if(a instanceof Comparable && b instanceof Comparable){
                        return ((Comparable) a).compareTo((Comparable) b);
                    } else{
                        return 0;
                    }
                }
            }
        };
    }

}
