/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import rtdc.core.Session;
import rtdc.core.impl.Storage;
import rtdc.core.model.User;
import rtdc.core.service.Service;
import rtdc.core.util.Cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence title;
    private AbstractFragment fragment;

    private boolean isAtHome = true;

    private ArrayAdapter<FragmentType> adapter;
    private int lastClicked = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar() call

        title = getTitle();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // NAVIGATION LIST VIEW
        AdapterView navListView = (AdapterView) findViewById(R.id.nav_list);

        // Set the drawer menu contents depending on what the user's permission is
        User sessionUser = Session.getCurrentSession().getUser();
        ArrayList<FragmentType> fragmentTypes = new ArrayList<>();
        if(sessionUser.getPermission().equals(User.Permission.ADMIN)) {
            fragmentTypes = new ArrayList<FragmentType>(Arrays.asList(FragmentType.MANAGE_UNITS, FragmentType.MANAGE_USERS, FragmentType.MESSAGES, FragmentType.PROFILE));
        }else if(sessionUser.getPermission().equals(User.Permission.MANAGER))
            fragmentTypes = new ArrayList<FragmentType>(Arrays.asList(FragmentType.CAPACITY_OVERVIEW, FragmentType.ACTION_PLAN, FragmentType.MESSAGES, FragmentType.PROFILE));
        else if(sessionUser.getPermission().equals(User.Permission.USER))
            fragmentTypes = new ArrayList<FragmentType>(Arrays.asList(FragmentType.ACTION_PLAN, FragmentType.MESSAGES, FragmentType.PROFILE));
        adapter = new navAdapter(fragmentTypes, this);

        navListView.setAdapter(adapter);

        // DRAWER MENU
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.action_new, R.string.action_delete) {

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
        selectItem(adapter.getItem(position));
    }

    private void selectItem(FragmentType type) {
        isAtHome = false;

        if(type == FragmentType.PROFILE){
            Cache.getInstance().put("user", Session.getCurrentSession().getUser());
            Bootstrapper.FACTORY.newDispatcher().goToEditUser(null);
        }else {
            goToFragment(type);

            // Update the title
            title = type.getTitle();
            setTitle(title);

            lastClicked = adapter.getPosition(type);
            adapter.notifyDataSetChanged();
        }

        // Close the drawer
        drawerLayout.closeDrawers();
    }

    private void goToFragment(int position){
        FragmentType type = adapter.getItem(position);
        goToFragment(type);
    }

    public void goToFragment(FragmentType type){
        switch(type){
            case CAPACITY_OVERVIEW:
                fragment = new CapacityOverviewFragment();
                if(Session.getCurrentSession().getUser().getPermission().equals(User.Permission.MANAGER))
                    isAtHome = true;
                break;
            case ACTION_PLAN:
                fragment = new ActionPlanFragment();
                if(Session.getCurrentSession().getUser().getPermission().equals(User.Permission.USER))
                    isAtHome = true;
                break;
            case MESSAGES:
                fragment = new MessagesFragment();
                break;
            case MANAGE_UNITS:
                if(Session.getCurrentSession().getUser().getPermission().equals(User.Permission.MANAGER))
                    isAtHome = true;
                fragment = new ManageUnitsFragment();
                break;
            case MANAGE_USERS:
                fragment = new ManageUsersFragment();
                break;
        }

        // Insert the fragment by replacing any existing fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        transaction.replace(R.id.main_fragment_wrapper, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent.getExtras() != null){
            Object fragment = intent.getExtras().get("fragment");
            if (fragment instanceof FragmentType)
                selectItem((FragmentType) fragment);
        }
    }

    private class navAdapter extends ArrayAdapter<FragmentType> {

        private LayoutInflater inflater;

        public navAdapter(List<FragmentType> titles, Context context) {
            super(context, R.layout.drawer_list_item, titles);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = inflater.inflate(R.layout.drawer_list_item, parent, false);

            FragmentType type = adapter.getItem(position);

            TextView textView = (TextView) view.findViewById(R.id.navTextView);
            textView.setText(type.getTitle());

            ImageView iconView = (ImageView) view.findViewById(R.id.navIcon);
            switch(type){
                case CAPACITY_OVERVIEW:
                    iconView.setImageResource(R.drawable.ic_hotel_white_24dp);
                    break;
                case ACTION_PLAN:
                    iconView.setImageResource(R.drawable.ic_assignment_turned_in_white_24dp);
                    break;
                case MESSAGES:
                    iconView.setImageResource(R.drawable.ic_chat_white_24dp);
                    break;
                case MANAGE_UNITS:
                    iconView.setImageResource(R.drawable.ic_local_hospital_white_24dp);
                    break;
                case MANAGE_USERS:
                    iconView.setImageResource(R.drawable.ic_build_white_24dp);
                    break;
                case PROFILE:
                    iconView.setImageResource(R.drawable.ic_account_circle_white_24dp);
                    break;
                default:
                    iconView.setImageResource(R.drawable.ic_mode_edit_white_24dp);
            }

            view.setTag(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {selectItem(Integer.parseInt(v.getTag().toString()));
                }
            });

            if(position == lastClicked){
                view.setBackgroundColor(getResources().getColor(R.color.RTDC_black));
            } else {
                view.setBackgroundColor(getResources().getColor(R.color.RTDC_grey));
            }

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