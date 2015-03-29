package rtdc.android;

import android.app.Application;
import android.content.Context;
import rtdc.android.impl.AndroidFactory;
import rtdc.core.Bootstrapper;

public class Rtdc extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        Bootstrapper.initialize(new AndroidFactory());
    }

    public static Context getAppContext() {
        return Rtdc.context;
    }
}
