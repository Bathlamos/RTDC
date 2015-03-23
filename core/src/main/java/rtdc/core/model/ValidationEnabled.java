package rtdc.core.model;

import rtdc.core.exception.ValidationException;

public interface ValidationEnabled<T> {

    public boolean validate(T property) throws ValidationException;

}
