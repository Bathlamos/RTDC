package rtdc.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import rtdc.android.impl.AndroidFactory;
import rtdc.core.Bootstrapper;
import rtdc.core.view.BootstrapperView;

public class Rtdc extends Application implements BootstrapperView {

    private static Context context;
    public static final String AUTH_TOKEN_KEY = "authTokenKey";
    private static SharedPreferences settings;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        Bootstrapper.initialize(new AndroidFactory(), this);
    }

    public static Context getAppContext() {
        return Rtdc.context;
    }

    @Override
    public void saveAuthenticationToken(String authToken) {
        settings.edit().putString(AUTH_TOKEN_KEY, authToken).commit();
    }

    @Override
    public String getAuthenticationToken() {
        return settings.getString(AUTH_TOKEN_KEY, null);
    }

}
