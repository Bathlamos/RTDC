package rtdc.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import java.io.IOException;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("accounts");
        tabSpec.setContent(R.id.tabAccounts);
        tabSpec.setIndicator("Manage Accounts");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("units");
        tabSpec.setContent(R.id.tabUnits);
        tabSpec.setIndicator("Manage Units");
        tabHost.addTab(tabSpec);
    }
}
