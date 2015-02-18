package rtdc.android;

import android.app.Application;
import android.content.Context;

public class RTDC extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        RTDC.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return RTDC.context;
    }
}
