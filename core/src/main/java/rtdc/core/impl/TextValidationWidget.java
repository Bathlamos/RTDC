package rtdc.core.impl;

public interface TextValidationWidget {

    String getInputText();

    void setInputText(String text);

    boolean isEmpty();

    String getError();

    void setError(String error);

    void clearError();

}
