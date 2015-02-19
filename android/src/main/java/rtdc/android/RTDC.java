package rtdc.android;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import rtdc.android.impl.AndroidFactory;
import rtdc.android.presenter.LoginActivity;
import rtdc.core.Bootstrapper;
import rtdc.core.controller.BootstrapperController;
import rtdc.core.view.BootstrapperView;

public class Rtdc extends Application implements BootstrapperView{

    private static Context context;
    public static final String AUTH_TOKEN_KEY = "authTokenKey",
        PREFS_NAME = "RtdcPrefs";

    private static SharedPreferences settings;
    private BootstrapperController controller = new BootstrapperController(this);

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Bootstrapper.initialize(new AndroidFactory());
        settings = getSharedPreferences(PREFS_NAME, 0);
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
        return settings.getString(AUTH_TOKEN_KEY, null) != null;
    }

    @Override
    public String getAuthenticationToken() {
        return settings.getString(AUTH_TOKEN_KEY, "");
    }

    @Override
    public void goToLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(context, CapacityOverviewActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
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
