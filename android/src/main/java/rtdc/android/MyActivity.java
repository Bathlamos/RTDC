package rtdc.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;
import com.couchbase.lite.auth.Authenticator;
import com.couchbase.lite.auth.BasicAuthenticator;
import com.couchbase.lite.replicator.Replication;
import rtdc.core.model.Unit;

import java.io.IOException;
import java.net.URL;

public class MyActivity extends Activity {

    private static final String TAG = "MyActivity";

    private Manager manager;
    private Database db;
    private static Context mContext;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mContext = getApplicationContext();
        try {
            /*
             * In Java the Manager instance and all the objects descending
             * from it may be used on any thread.
             */
            manager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
            db = manager.getDatabase("rtdc");

            URL url = new URL("https://legault.cc:80");
            Replication push = db.createPushReplication(url);
            Replication pull = db.createPullReplication(url);
            pull.setContinuous(true);
            push.setContinuous(true);
            Authenticator auth = new BasicAuthenticator("Administrator", "password");
            push.setAuthenticator(auth);
            pull.setAuthenticator(auth);

            Document document = db.createDocument();
            Unit unit = new Unit();
            unit.setName("Alistair's Unit");
            document.putProperties(unit.getProperties());
            document.createRevision().save();

        } catch (IOException e) {
            Log.e(TAG, "Cannot create Manager instance", e);
            return;
        } catch(CouchbaseLiteException e){
            Log.e(TAG, "Cannot get Database or create object", e);
            return;
        }
    }
}
