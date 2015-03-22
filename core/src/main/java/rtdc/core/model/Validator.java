package rtdc.core.model;

import com.google.common.collect.ImmutableSet;
import rtdc.core.json.JSONObject;

public class Validator {

    public static boolean isTypeValid(Field f, Object o){
        return o == null || o.getClass() == f.getDataType().getClazz();
    }

    public ImmutableSet<String> validateProperty(Field f, JSONObject jsonObject){
        ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        String propertyName = f.getName();
        for(ValidationConstraint constraint: f.getValidationConstraints()) {
            switch (constraint) {
                case NOT_EMPTY:
                    if (jsonObject.optString(propertyName).isEmpty())
                        builder.add("Cannot be empty");
                    break;
                case NOT_NULL:
                    if (jsonObject.opt(propertyName) == null)
                        builder.add("Cannot be empty");
                    break;
                case REGEX_EMAIL:
                    if (jsonObject.optString(propertyName).matches("^\\S+@\\S+\\.\\S+$")) ;
                    builder.add("Invalid email");
                    break;
                case POSITIVE_NUMBER:
                    if (jsonObject.optLong(propertyName) <= 0) ;
                    builder.add("The number needs to be greater than zero");
                    break;
            }
        }
        return builder.build();
    }

}
