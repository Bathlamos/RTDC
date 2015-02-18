package rtdc.core.service;

public interface AsyncCallback<T> {

    void onSuccess(T data);

    void onError(String message);

}
