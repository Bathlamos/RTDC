package rtdc.core.service;

public interface AsyncCallback<T> {

    void onCallback(T data);

}
