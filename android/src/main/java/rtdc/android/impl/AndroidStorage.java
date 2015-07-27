package rtdc.android.impl;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import rtdc.android.AndroidBootstrapper;
import rtdc.core.impl.Storage;

import java.util.logging.Logger;

public class AndroidStorage implements Storage
{
    private static final Logger LOG = Logger.getLogger(AndroidStorage.class.getSimpleName());
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
        LOG.info("Adding " + key + ":" + data);
        settings.edit().putString(key, data).commit();
    }

    @Override
    public String retrieve(String key) {
        LOG.info("Retrieving " + key + ":" + settings.getString(key, ""));
        return settings.getString(key, "");
    }

    @Override
    public void remove(String key) {
        settings.edit().remove(key).commit();
    }
}
