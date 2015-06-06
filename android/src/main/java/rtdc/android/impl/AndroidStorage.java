package rtdc.android.impl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import rtdc.android.AndroidBootstrapper;
import rtdc.core.impl.Storage;

public class AndroidStorage implements Storage
{

    private static SharedPreferences settings;
    private static AndroidStorage INSTANCE = null;

    private AndroidStorage(){
        settings = PreferenceManager.getDefaultSharedPreferences(AndroidBootstrapper.getAppContext());
    }

    public static AndroidStorage get(){
        if(INSTANCE == null)
            INSTANCE = new AndroidStorage();
        return INSTANCE;
    }

    @Override
    public void add(String key, String data) {
        settings.edit().putString(key, data).commit();
    }

    @Override
    public String retrieve(String key) {
        return settings.getString(key, "");
    }
}
