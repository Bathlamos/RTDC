package rtdc.core.model;

import rtdc.core.exception.ValidationException;

public class SimpleValidator {

    public boolean expectNotNull(Object value){
        if(value == null)
            throw new ValidationException("Expected a value");
        return true;
    }

    public boolean expectNotEmpty(String value){
        expectNotNull(value);
        if(value.isEmpty())
            throw new ValidationException("Expected a non-empty value");
        return true;
    }

    public boolean expectPositiveNumber(int number){
        if(number <= 0)
            throw new ValidationException("Expected a positive number");
        return true;
    }

    public boolean expectEmail(String value){
        if(value != null && value.matches("^\\S+@\\S+\\.\\S+$"))
            throw new ValidationException("Invalid email");
        return true;
    }

}
