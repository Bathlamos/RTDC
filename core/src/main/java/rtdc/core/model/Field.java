package rtdc.core.model;

public class Field {

    private final String propertyName;
    private final DataType dataType;
    private final DataType generics;
    private final ValidationConstraint[] validationConstraints;

    public Field(String propertyName, DataType dataType, ValidationConstraint... validationConstraints){
        this(propertyName, dataType, null, validationConstraints);
    }

    public Field(String propertyName, DataType dataType, DataType generics, ValidationConstraint... validationConstraints){
        this.propertyName = propertyName;
        this.dataType = dataType;
        this.generics = generics;
        if(validationConstraints == null)
            this.validationConstraints = new ValidationConstraint[0];
        else
            this.validationConstraints = validationConstraints;
    }

    /**
     * String is immutable, so giving direct references doesn't matter
     */
    public String getName() {
        return propertyName;
    }

    public DataType getDataType() {
        return dataType;
    }

    public DataType getGenerics() {
        return generics;
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
        return !(obj instanceof  Field) || getName().equals(((Field) obj).getName());
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

}
