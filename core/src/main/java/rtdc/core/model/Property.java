package rtdc.core.model;

/**
 * Immutable class
 */
public final class Property{
    public static enum DataType {BOOLEAN, INT, LONG, STRING, JSON_ARRAY, JSON_OBJECT, UNIT, USER}
    public static enum ValidationConstraint {NOT_NULL, NOT_EMPTY, REGEX_EMAIL, POSITIVE_NUMBER}

    private final String propertyName;
    private final DataType dataType;
    private final ValidationConstraint[] validationConstraints;

    public Property(String propertyName, DataType dataType, ValidationConstraint... validationConstraints){
        this.propertyName = propertyName;
        this.dataType = dataType;
        if(validationConstraints == null)
            this.validationConstraints = new ValidationConstraint[0];
        else
            this.validationConstraints = validationConstraints;
    }

    /**
     * String is immutable, so giving direct references doesn't matter
     */
    public String getPropertyName() {
        return propertyName;
    }

    public DataType getDataType() {
        return dataType;
    }

    /**
     * Making a defensive copy
     */
    public ValidationConstraint[] getValidationConstraints() {
        ValidationConstraint[] defensiveCopy = new ValidationConstraint[validationConstraints.length];
        System.arraycopy(validationConstraints, 0, defensiveCopy, 0, validationConstraints.length);
        return defensiveCopy;
    }

    @Override
    public boolean equals(Object obj) {
        return !(obj instanceof  Property) || getPropertyName().equals(((Property) obj).getPropertyName());
    }

    @Override
    public int hashCode() {
        return getPropertyName().hashCode();
    }
}