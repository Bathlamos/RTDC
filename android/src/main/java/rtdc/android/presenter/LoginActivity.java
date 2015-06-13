package rtdc.android.presenter;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.LoginController;
import rtdc.core.impl.UiElement;
import rtdc.core.view.LoginView;

public class LoginActivity extends AbstractActivity implements LoginView {

    // UI references.
    private AndroidUiString mEmailView, mPasswordView;

    private LoginController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);                   // Setting toolbar as the ActionBar with setSupportActionBar() call

        setTitle(R.string.title_activity_login);

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



