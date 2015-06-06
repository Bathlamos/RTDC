package rtdc.core.impl;

public interface Storage {

    String KEY_AUTH_TOKEN = "AUTH_TOKEN";

    void add( String key, String data);

    String retrieve(String key);

}
