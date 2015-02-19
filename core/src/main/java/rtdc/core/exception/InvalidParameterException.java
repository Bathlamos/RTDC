package rtdc.core.exception;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class InvalidParameterException extends ApiException{

    public InvalidParameterException(String message){
        super(message);
    }


    public static <T> InvalidParameterException fromConstraintViolations(Set<ConstraintViolation<T>> violations){
        StringBuilder sb = new StringBuilder();
        for(ConstraintViolation violation: violations)
            sb.append(String.format("Peroperty %s: %s", violation.getPropertyPath().toString(), violation.getMessage()));
        return new InvalidParameterException(sb.toString());
    }

}
