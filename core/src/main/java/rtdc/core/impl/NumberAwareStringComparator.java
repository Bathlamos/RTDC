package rtdc.core.impl;

import rtdc.core.model.Unit;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberAwareStringComparator implements Comparator<Unit> {
    public static final NumberAwareStringComparator INSTANCE = new NumberAwareStringComparator();

    private static final Pattern PATTERN = Pattern.compile("(\\D*)(\\d*)");

    public NumberAwareStringComparator() {
    }

    public int compare(Unit unit1, Unit unit2) {
        Matcher m1 = PATTERN.matcher(unit1.getName());
        Matcher m2 = PATTERN.matcher(unit2.getName());

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
                return +1;
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
                m1.hitEnd()                ? -1 : +1;
    }
}