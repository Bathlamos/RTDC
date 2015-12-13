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

import rtdc.core.exception.ValidationException;
import rtdc.core.i18n.MessageBundle;

public class SimpleValidator {

    public static boolean isNotNull(Object value){
        if(value == null)
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("isNull"));
        return true;
    }

    public static boolean isNotEmpty(String value){
        isNotNull(value);
        if(value.isEmpty())
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("isEmpty"));
        return true;
    }

    public static boolean isPositiveNumber(int number){
        if(number <= 0)
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("isNotPositiveNumber"));
        return true;
    }

    public static boolean isNumber(String value){
        isNotEmpty(value);
        if(!value.matches("-?\\d+(\\.\\d+)?"))
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("isNotNumber"));
        return true;
    }

    /*
        -------------------- User validation methods --------------------
     */

    public static boolean validateUser(User user){
        validateUsername(user.getUsername());
        validateEmail(user.getEmail());
        validatePersonFirstName(user.getFirstName());
        validatePersonLastName(user.getLastName());
        return true;
    }

    public static boolean validateUsername(String value){
        isNotEmpty(value);

        final int minLength = 6;
        if(value.length() < minLength)
            ;//throw new ValidationException(MessageFormat.format(MessageBundle.getBundle(Locale.ENGLISH).getString("usernameToShort"), minLength));
        else if(!value.matches("(\\w|\\-)+"))
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("usernameAsIllegalCharacters"));
        return true;
    }

    public static boolean validatePassword(String value){
        isNotEmpty(value);

        final int minLength = 8;
        if(value.length() < minLength)
            ;//throw new ValidationException(MessageFormat.format(MessageBundle.getBundle(Locale.ENGLISH).getString("passwordToShort"), minLength));
        else if(!value.matches("^(?=.*[0-9])(?=.*[A-Za-z]).+$"))
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("passwordAsIllegalCharacters"));
        return true;
    }

    public static boolean validateEmail(String value){
        isNotEmpty(value);
        if(!value.matches("^\\S+@\\S+\\.\\S+$"))
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidEmail"));
        return true;
    }

    public static boolean validatePersonFirstName(String value){
        isNotEmpty(value);

        if(!value.matches("([A-Za-z]|\\-)+"))
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidName"));
        return true;
    }

    public static boolean validatePersonLastName(String value){
        isNotEmpty(value);

        if(!value.matches("([A-Za-z]|\\-)+"))
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidName"));
        return true;
    }

    /*
        -------------------- Unit validation methods --------------------
     */

    public static boolean validateUnit(Unit unit){
        validateUnitName(unit.getName());
        validateAvailableBeds(unit);
        validatePotentialDc(unit);
        validateDcByDeadline(unit);
        validateAdmitsByDeadline(unit);
        return true;
    }

    public static boolean validateUnitName(String value){
        isNotEmpty(value);
        return true;
    }

    public static boolean validateAvailableBeds(Unit unit){
        isNotNull(unit.getAvailableBeds());

        if(unit.getAvailableBeds() > unit.getTotalBeds())
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidNumberOfAvailableBeds"));
        return true;
    }

    public static boolean validatePotentialDc(Unit unit){
        isNotNull(unit.getPotentialDc());

        if(unit.getPotentialDc() > unit.getTotalBeds())
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidPotentialDischarge"));
        return true;
    }

    public static boolean validateDcByDeadline(Unit unit){
        isNotNull(unit.getDcByDeadline());

        if(unit.getDcByDeadline() > unit.getPotentialDc())
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidDischargeByDeadline"));
        return true;
    }

    public static boolean validateAdmitsByDeadline(Unit unit){
        isNotNull(unit.getAdmitsByDeadline());

        if(unit.getAdmitsByDeadline() > unit.getTotalAdmits())
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidAdmitsByDeadline"));
        return true;
    }

    /*
        -------------------- Action validation methods --------------------
     */

    public static boolean validateAction(Action action){
        validateActionDescription(action.getDescription());
        return true;
    }

    public static boolean validateActionDescription(String value){
        isNotEmpty(value);

        if(value.length() > 2500)
            ;//throw new ValidationException(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidActionDescription"));
        return true;
    }
}
