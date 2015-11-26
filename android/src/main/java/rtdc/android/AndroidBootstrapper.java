package rtdc.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import rtdc.android.impl.AndroidFactory;
import rtdc.android.voip.LiblinphoneThread;
import rtdc.core.Bootstrapper;
import rtdc.core.Session;

public class AndroidBootstrapper extends Activity {

    private static Context context;

    static {
        Bootstrapper.initialize(new AndroidFactory());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return AndroidBootstrapper.context;
    }

}
