package rtdc.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import rtdc.android.presenter.CreateUserActivity;
import rtdc.android.presenter.fragments.UserFragment;

import java.io.IOException;

public class MyActivity extends Activity implements UserFragment.OnFragmentInteractionListener {
    /**
     * Called when the activity is first created.
     */

    private final static int TAB_ACCOUNTS = 0;
    private final static int TAB_UNITS = 1;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add:
                TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
                if (tabHost.getCurrentTab() == TAB_ACCOUNTS) {
                    Intent intent = new Intent(this, CreateUserActivity.class);
                    startActivity(intent);
                } else if (tabHost.getCurrentTab() == TAB_UNITS) {
                    // TODO: Intent to navigate to Units list
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(int userId) {

    }
}
