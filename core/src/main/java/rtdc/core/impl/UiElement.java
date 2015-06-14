package rtdc.core.impl;

public interface UiElement<T> {

    T getValue();
    void setValue(T value);

    String getErrorMessage();
    void setErrorMessage(String errorMessage);

    void setFocus(boolean hasFocus);

}
