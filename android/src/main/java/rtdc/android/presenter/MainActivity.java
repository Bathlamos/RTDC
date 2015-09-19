package rtdc.android.presenter;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import rtdc.android.presenter.fragments.*;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.impl.Storage;
import rtdc.core.service.Service;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private ListView mDrawerList;
    private CharSequence title;
    private AbstractFragment fragment;

    private boolean isAtHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call

        title = getTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.action_add, R.string.action_delete) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setTitle(title);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(R.string.title_drawer_menu);
                invalidateOptionsMenu();
                syncState();
            }
        };

        Button buttonSignOut = (Button) findViewById(R.id.button_sign_out);

        buttonSignOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                Bootstrapper.FACTORY.getStorage().remove(Storage.KEY_AUTH_TOKEN);
                Service.logout();
                startActivity(intent);
                finish();
            }
        });

        //TODO: Change when permissions are implemented
        String[] mPlanetTitles = new String[]{"Capacity Overview", "Action Plan", "Communication Hub", "User Profile", "Manage Users", "Manage Units"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.nav_list);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        selectItem(0); // Opens the capacity overview by default
    }
    
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        isAtHome = false;

        switch(position){
            case 0:
                fragment = new CapacityOverviewFragment();
                isAtHome = true;
                break;
            case 1:
                fragment = new ActionPlanFragment();
                break;
            case 2:
                //fragment = new CommunicationHubFragment();
                //fragment = new OldCommunicationHub();
                fragment = new CommunicationHubContactFragment();
                break;
            case 4:
                fragment = new ManageUsersFragment();
                break;
            case 5:
                fragment = new ManageUnitsFragment();
                break;
            default:
                fragment = new CapacityOverviewFragment();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment_wrapper, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawers();
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(isAtHome){
            super.onBackPressed();
        } else {
            selectItem(0);
        }
    }
}