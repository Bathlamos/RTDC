package rtdc.android.presenter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import rtdc.android.presenter.fragments.*;
import rtdc.android.R;
import rtdc.core.Bootstrapper;
import rtdc.core.impl.Storage;
import rtdc.core.service.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence title;
    private AbstractFragment fragment;

    private boolean isAtHome = true;

    private ArrayList<String> navTitles;
    private ArrayAdapter<String> adapter;
    private RelativeLayout lastClicked = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call

        title = getTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // NAVIGATION LIST VIEW
        // TODO - Replace with enum
        String capacityTitle = getResources().getString(R.string.title_capacity_overview);
        String actionTitle = getResources().getString(R.string.title_action_plan);
        String commTitle = getResources().getString(R.string.title_communication_hub);
        String manageUnitsTitle = getResources().getString(R.string.title_manage_units);
        String manageUsersTitle = getResources().getString(R.string.title_manage_users);

        AdapterView navListView = (AdapterView) findViewById(R.id.nav_list);
        //TODO: Change when permissions are implemented
        navTitles = new ArrayList<String>(Arrays.asList(capacityTitle, actionTitle, commTitle, manageUnitsTitle, manageUsersTitle));
        adapter = new navAdapter(navTitles, this);
        navListView.setAdapter(adapter);

        // DRAWER MENU
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.action_add, R.string.action_delete) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                setTitle(title);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
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

        drawerLayout.setDrawerListener(drawerToggle);

        // Doing this check first prevents the fragment from being reloaded to home fragment when the screen orientation is changed
        if(savedInstanceState == null){
            if(getIntent() != null)
                onNewIntent(getIntent());
            if(fragment == null)
                selectItem(0); // Opens the capacity overview by default
        }
    }

    /**
     * Swaps fragments in the main content view
     */
    private void selectItem(int position) {
        isAtHome = false;

        goToFragment(position);

        // Update the title, and close the drawer
        title = navTitles.get(position);
        setTitle(title);
        drawerLayout.closeDrawers();
    }

    public void goToFragment(int id){
        switch(id){
            case 0:
                fragment = new CapacityOverviewFragment();
                isAtHome = true;
                break;
            case 1:
                fragment = new ActionPlanFragment();
                break;
            case 2:
                fragment = new CommunicationHubFragment();
                break;
            case 3:
                fragment = new ManageUnitsFragment();
                break;
            case 4:
                fragment = new ManageUsersFragment();
                break;
            default:
                fragment = new CapacityOverviewFragment();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment_wrapper, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        int position = intent.getIntExtra("fragment", -1);
        if (position != -1)
            goToFragment(position);
    }

    private class navAdapter extends ArrayAdapter<String> {

        private LayoutInflater inflater;

        public navAdapter(List<String> titles, Context context) {
            super(context, R.layout.drawer_list_item, titles);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = inflater.inflate(R.layout.drawer_list_item, parent, false);

            if (lastClicked == null) {
                lastClicked = (RelativeLayout) view.findViewById(R.id.navLayout);
                lastClicked.setBackgroundColor(getResources().getColor(R.color.RTDC_black));
            }

            String currentTitle = navTitles.get(position);

            TextView textView = (TextView) view.findViewById(R.id.navTextView);
            textView.setText(currentTitle);

            ImageView iconView = (ImageView) view.findViewById(R.id.navIcon);
            switch(position){
                case 0:
                    iconView.setImageResource(R.drawable.ic_hotel_white_24dp);
                    break;
                case 1:
                    iconView.setImageResource(R.drawable.ic_assignment_turned_in_white_24dp);
                    break;
                case 2:
                    iconView.setImageResource(R.drawable.ic_chat_white_24dp);
                    break;
                case 3:
                    iconView.setImageResource(R.drawable.ic_local_hospital_white_24dp);
                    break;
                case 4:
                    iconView.setImageResource(R.drawable.ic_build_white_24dp);
                    break;
                default:
                    iconView.setImageResource(R.drawable.ic_mode_edit_white_24dp);
            }

            view.setTag(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem(Integer.parseInt(v.getTag().toString()));
                    lastClicked.setBackgroundColor(getResources().getColor(R.color.RTDC_grey));

                    RelativeLayout navLayout = (RelativeLayout) v.findViewById(R.id.navLayout);
                    navLayout.setBackgroundColor(getResources().getColor(R.color.RTDC_black));
                    lastClicked = navLayout;
                }
            });

            return view;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
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



