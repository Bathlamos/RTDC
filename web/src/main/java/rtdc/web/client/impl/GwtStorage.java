package rtdc.web.client.impl;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import rtdc.core.impl.Storage;

public class GwtStorage implements Storage {

    private static GwtStorage INSTANCE;

    private GwtStorage(){
        Cookies.setUriEncode(false);
    }

    @Override
    public void add(String key, String data) {
        Cookies.setCookie(key, data);
    }

    @Override
    public String retrieve(String key) {
        return Cookies.getCookie(key);
    }

    @Override
    public void remove(String key) {
        Cookies.removeCookie(key);
    }

    public static GwtStorage get(){
        if(INSTANCE == null)
            INSTANCE = new GwtStorage();
        return INSTANCE;
    }
}
