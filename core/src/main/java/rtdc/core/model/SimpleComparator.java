package rtdc.core.model;

import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleComparator {

    private static final Logger logger = Logger.getLogger(SimpleComparator.class.getCanonicalName());

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
                    Object valueB = b.getValue(property);
                    if(valueA == valueB)
                        return 0;
                    else if(valueA == null)
                        return -1;
                    else if(valueB == null)
                        return 1;
                    else if(valueA instanceof Comparable) {
                        logger.log(Level.INFO, ((Comparable) valueA).compareTo(valueB) + "");
                        return ((Comparable) valueA).compareTo(valueB);
                    }else if(valueA.getClass().isPrimitive() && valueB.getClass().isPrimitive())
                        return (int) valueA - (int) valueB;
                     else{
                        return 0;
                    }
                }
            }
        };
    }

}
