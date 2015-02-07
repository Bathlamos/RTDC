package rtdc.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Manager manager;

        Context mContext = getApplicationContext();
        try {
            /*
             * In Java the Manager instance and all the objects descending
             * from it may be used on any thread.
             */
            manager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            Log.e(TAG, "Cannot create Manager instance", e);
            return;
        }
    }
}
