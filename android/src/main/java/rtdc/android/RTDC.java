package rtdc.android;

import android.app.Application;
import android.content.Context;

public class Rtdc extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        Rtdc.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Rtdc.context;
    }
}
