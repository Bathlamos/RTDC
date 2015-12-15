/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.core.model;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains comparators used throughout the application
 */
public class SimpleComparator {

    private static final NumberAwareStringComparator NUMBER_AWARE_STRING_COMPARATOR = new NumberAwareStringComparator();

    public static <T extends RootObject> Builder<T> forProperty(final ObjectProperty<T> property) {
        return new Builder<>(property);
    }

    /**
     * Orders objects according to an arbitrary ObjectProperty
     * @param <T> The type of object to order
     */
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

    /**
     * Comparator that orders all the numbers first, then the number-less items, in a list
     */
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
