package rtdc.android.presenter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.*;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.LoginController;
import rtdc.core.impl.UiElement;
import rtdc.core.view.LoginView;

public class LoginActivity extends AbstractActivity implements LoginView {

    // UI references.
    private AndroidUiString mEmailView, mPasswordView;

    private LoginController controller;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        setTitle(R.string.title_activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitle = getTitle();
        mDrawerTitle = "Navigation Drawer";

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                toolbar,
                R.string.action_add,
                R.string.action_delete)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                toolbar.setTitle(mTitle);
                invalidateOptionsMenu();
                syncState();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(mDrawerTitle);
                invalidateOptionsMenu();
                syncState();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);



        // Set up the login form.
        mEmailView = (AndroidUiString) findViewById(R.id.email);

        mPasswordView = (AndroidUiString) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    controller.login();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.login();
            }
        });

        if(controller == null)
            controller = new LoginController(this);
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
    public UiElement<String> getUsernameUiElement() {
        return mEmailView;
    }

    @Override
    public UiElement<String> getPasswordUiElement() {
        return mPasswordView;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}



