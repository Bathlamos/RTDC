package rtdc.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import rtdc.android.impl.AndroidFactory;
import rtdc.core.Bootstrapper;

public class AndroidBootstrapper extends Activity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        Bootstrapper.initialize(new AndroidFactory());
    }

    public static Context getAppContext() {
        return AndroidBootstrapper.context;
    }

}
