package rtdc.android.presenter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import rtdc.android.R;
import rtdc.android.impl.AndroidUiDropdown;
import rtdc.android.impl.AndroidUiString;
import rtdc.core.controller.AddUserController;
import rtdc.core.exception.ValidationException;
import rtdc.core.i18n.MessageBundle;
import rtdc.core.impl.UiDropdown;
import rtdc.core.impl.UiElement;
import rtdc.core.model.SimpleValidator;
import rtdc.core.model.User;
import rtdc.core.view.AddUserView;

import java.lang.reflect.Field;
import java.util.Locale;

public class CreateUserActivity extends AbstractDialog implements AddUserView {

    private AddUserController controller;

    private AndroidUiString usernameEdit, passwordEdit, confirmPasswordEdit, emailEdit, firstNameEdit, lastNameEdit, phoneEdit;
    private AndroidUiDropdown roleSpinner, permissionSpinner;
    private TextView confirmPasswordText;
    private CheckBox passwordChange;
    private boolean hideDeleteButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Add the action bar at the top.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameEdit = (AndroidUiString) findViewById(R.id.usernameEdit);
        usernameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateUsernameUiElement();
            }
        });

        passwordChange = (CheckBox) findViewById(R.id.passwordChange);

        passwordEdit = (AndroidUiString) findViewById(R.id.passwordEdit);
        passwordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && passwordChange.isChecked())
                    controller.validatePasswordUiElement();
            }
        });

        confirmPasswordText = (TextView) findViewById(R.id.confirmPasswordText);
        confirmPasswordEdit = (AndroidUiString) findViewById(R.id.confirmPasswordEdit);
        confirmPasswordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && passwordChange.isChecked())
                    controller.validateConfirmPasswordUiElement();
            }
        });

        emailEdit = (AndroidUiString) findViewById(R.id.emailEdit);
        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateEmailUiElement();
            }
        });

        firstNameEdit = (AndroidUiString) findViewById(R.id.firstNameEdit);
        firstNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateFirstNameUiElement();
            }
        });

        lastNameEdit = (AndroidUiString) findViewById(R.id.lastNameEdit);
        lastNameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validateLastNameUiElement();
            }
        });

        phoneEdit = (AndroidUiString) findViewById(R.id.phoneEdit);
        phoneEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    controller.validatePhoneNumberUiElement();
            }
        });

        roleSpinner = (AndroidUiDropdown) findViewById(R.id.roleSpinner);
        permissionSpinner = (AndroidUiDropdown) findViewById(R.id.permissionSpinner);

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        }
        catch (Exception e) {
            // presumably, not relevant
        }

        passwordChange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                showOrHidePasswordFields(isChecked);
            }
        });

        if(controller == null)
            controller = new AddUserController(this);

        if(controller.isNewUser()) {
            passwordChange.setChecked(true);
            passwordChange.setVisibility(View.GONE);
            showOrHidePasswordFields(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        MenuItem deleteUser = menu.findItem(R.id.action_discard_user);
        if(hideDeleteButton) deleteUser.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       switch (item.getItemId()) {
           //noinspection SimplifiableIfStatement
           case R.id.action_save_new_user:
               // Check to see if all fields are valid before proceeding
               controller.validateUsernameUiElement();
               if(passwordChange.isChecked()) {
                   controller.validatePasswordUiElement();
                   controller.validateConfirmPasswordUiElement();
               }
               controller.validateEmailUiElement();
               controller.validateFirstNameUiElement();
               controller.validateLastNameUiElement();
               controller.validatePhoneNumberUiElement();

               if(usernameEdit.getError() != null || (passwordEdit.getError() != null && confirmPasswordEdit.getError() != null && passwordChange.isChecked())
                       || emailEdit.getError() != null || firstNameEdit.getError() != null || lastNameEdit.getError() != null || phoneEdit.getError() != null) {
                   new AlertDialog.Builder(this)
                           .setTitle(MessageBundle.getBundle(Locale.ENGLISH).getString("errorGeneral"))
                           .setMessage(MessageBundle.getBundle(Locale.ENGLISH).getString("invalidFields"))
                           .setNeutralButton(android.R.string.ok, null).show();
                   return true;
               }
               controller.addUser(passwordChange.isChecked());
               return true;
           case R.id.action_discard_user:
               new AlertDialog.Builder(this)
                   .setTitle("Confirm")
                   .setMessage("Delete current user?")
                   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int whichButton) {
                           controller.deleteUser();
                           Toast.makeText(CreateUserActivity.this, "User Deleted", Toast.LENGTH_SHORT).show();
                       }})
                   .setNegativeButton(android.R.string.no, null).show();
               return true;
           default:
               return super.onOptionsItemSelected(item);
       }
    }

    private void showOrHidePasswordFields(boolean show) {
        passwordEdit.setText("");
        confirmPasswordEdit.setText("");

        if(show) {
            passwordEdit.setVisibility(View.VISIBLE);
            confirmPasswordText.setVisibility(View.VISIBLE);
            confirmPasswordEdit.setVisibility(View.VISIBLE);
        } else {
            passwordEdit.setVisibility(View.GONE);
            confirmPasswordText.setVisibility(View.GONE);
            confirmPasswordEdit.setVisibility(View.GONE);
        }
    }

    @Override
    public UiElement<String> getUsernameUiElement() {
        return usernameEdit;
    }

    @Override
    public UiElement<String> getLastNameUiElement() {
        return lastNameEdit;
    }

    @Override
    public UiElement<String> getFirstNameUiElement() {
        return firstNameEdit;
    }

    @Override
    public UiElement<String> getEmailUiElement() {
        return emailEdit;
    }

    @Override
    public UiElement<String> getPhoneUiElement() {
        return phoneEdit;
    }

    @Override
    public UiDropdown<User.Role> getRoleUiElement() {
        return roleSpinner;
    }

    @Override
    public UiDropdown<User.Permission> getPermissionUiElement() {
        return permissionSpinner;
    }

    @Override
    public UiElement<String> getPasswordUiElement() {
        return passwordEdit;
    }

    @Override
    public UiElement<String> getConfirmPasswordUiElement() {
        return confirmPasswordEdit;
    }

    @Override
    public void hideDeleteButton() {
        hideDeleteButton = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
