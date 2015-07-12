package rtdc.core.model;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleComparator {

    private static final NumberAwareStringComparator NUMBER_AWARE_STRING_COMPARATOR = new NumberAwareStringComparator();

    public static <T extends RootObject> Builder<T> forProperty(final ObjectProperty<T> property) {
        return new Builder<>(property);
    }

    public static class Builder<T extends RootObject> {
        private boolean ascending;
        private final ObjectProperty<T> property;

        private Builder(ObjectProperty<T> property){
            this.property = property;
        }

        public Builder<T> setAscending(boolean ascending) {
            this.ascending = ascending;
            return this;
        }

        public Comparator<T> build(){
            return new Comparator<T>() {
                @Override
                public int compare(T a, T b) {
                    int result = 0; // a and b are a priori equal
                    if (a == b)
                        result = 0;
                    if (a == null)
                        result = -1;
                    else if (b == null)
                        result = 1;
                    else {
                        Object valueA = a.getValue(property);
                        Object valueB = b.getValue(property);
                        if (valueA == valueB)
                            result = 0;
                        else if (valueA == null)
                            result = -1;
                        else if (valueB == null)
                            result = 1;
                        else if (valueA instanceof String && valueB instanceof String)
                            result = NUMBER_AWARE_STRING_COMPARATOR.compare((String) valueA, (String) valueB);
                        else if (valueA instanceof Comparable)
                            result = ((Comparable) valueA).compareTo(valueB);
                        else if (valueA.getClass().isPrimitive() && valueB.getClass().isPrimitive())
                            // TODO: this is error prone and no way to sort primitives
                            result = (int) Math.signum((double) valueA - (double) valueB);
                    }

                    if(!ascending)
                        result *= -1;

                    return result;
                }
            };
        }

    }

    public static class NumberAwareStringComparator implements Comparator<String> {

        private static final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");

        public int compare(String a, String b) {
            Matcher m1 = PATTERN.matcher(a);
            Matcher m2 = PATTERN.matcher(b);

            // The only way find() could fail is at the end of a string
            while (m1.find() && m2.find()) {
                // matcher.group(1) fetches any non-digits captured by the
                // first parentheses in PATTERN.
                int nonDigitCompare = m1.group(1).compareTo(m2.group(1));
                if (0 != nonDigitCompare) {
                    return nonDigitCompare;
                }

                // matcher.group(2) fetches any digits captured by the
                // second parentheses in PATTERN.
                if (m1.group(2).isEmpty()) {
                    return m2.group(2).isEmpty() ? 0 : -1;
                } else if (m2.group(2).isEmpty()) {
                    return 1;
                }

                BigInteger n1 = new BigInteger(m1.group(2));
                BigInteger n2 = new BigInteger(m2.group(2));
                int numberCompare = n1.compareTo(n2);
                if (0 != numberCompare) {
                    return numberCompare;
                }
            }

            // Handle if one string is a prefix of the other.
            // Nothing comes before something.
            return m1.hitEnd() && m2.hitEnd() ? 0 :
                   m1.hitEnd()                ? -1 : 1;
        }
    }

}
