package rtdc.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import rtdc.android.impl.AndroidFactory;
import rtdc.android.presenter.LoginActivity;
import rtdc.core.Bootstrapper;
import rtdc.core.controller.BootstrapperController;
import rtdc.core.view.BootstrapperView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Rtdc extends Application implements BootstrapperView{

    private static Context context;
    public static final String AUTH_TOKEN_KEY = "authTokenKey";

    private static SharedPreferences settings;
    private BootstrapperController controller = new BootstrapperController(this);

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Bootstrapper.initialize(new AndroidFactory());
        settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        controller.init();
    }

    public static Context getAppContext() {
        return Rtdc.context;
    }

    public static SharedPreferences getAppPrefs() {
        return Rtdc.settings;
    }

    @Override
    public boolean hasAuthenticationToken() {
        Logger.getLogger("RTDC").log(Level.INFO, "HasAuthenticationToken : " + settings.getString(AUTH_TOKEN_KEY, ""));
        return !settings.getString(AUTH_TOKEN_KEY, "").isEmpty();
    }

    @Override
    public String getAuthenticationToken() {
        return settings.getString(AUTH_TOKEN_KEY, "");
    }

    @Override
    public void displayPermanentError(String title, String error) {
        displayPermanentError(title, error);
    }

    @Override
    public void displayError(String title, String error) {
        Toast.makeText(context, title + " : " + error, Toast.LENGTH_LONG).show();
    }
}
