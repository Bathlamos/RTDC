package rtdc.android.presenter;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import rtdc.android.R;
import rtdc.android.Rtdc;
import rtdc.android.impl.AndroidTextValidationWidget;
import rtdc.core.controller.LoginController;
import rtdc.core.view.LoginView;

public class LoginActivity extends AbstractActivity implements LoginView {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AndroidTextValidationWidget mPasswordView;

    private LoginController controller = new LoginController(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (AndroidTextValidationWidget) findViewById(R.id.password);
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
                if(mPasswordView.isEmpty()) mPasswordView.setError("Enter password");
                else controller.login();
            }
        });
    }

    @Override
    public String getUsername() {
        return mEmailView.getText().toString();
    }

    @Override
    public void setUsername(String username) {
        mEmailView.setText(username);
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        mPasswordView.setText(password);
    }

}



