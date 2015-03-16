package rtdc.core.impl;

public interface UiElement<T> {

    public T getValue();
    public void setValue(T value);

    public String getErrorMessage();
    public void setErrorMessage(String errorMessage);

}
