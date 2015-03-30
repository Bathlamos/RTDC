package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import rtdc.android.AdminActivity;
import rtdc.android.R;
import rtdc.core.controller.AddUserController;
import rtdc.core.json.JSONObject;
import rtdc.core.model.User;
import rtdc.core.util.Cache;
import rtdc.core.view.AddUserView;

import java.lang.reflect.Field;

public class CreateUserActivity extends AbstractActivity implements AddUserView {

    private AddUserController controller;

    private EditText usernameEdit, passwordEdit, emailEdit, firstNameEdit, lastNameEdit, phoneEdit;
    private Spinner roleSpinner, permissionSpinner;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setTitle(R.string.title_activity_create_user);

        usernameEdit = (EditText) findViewById(R.id.usernameEdit);
        passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        phoneEdit = (EditText) findViewById(R.id.phoneEdit);

        roleSpinner = (Spinner) findViewById(R.id.roleSpinner);
        ArrayAdapter<CharSequence> rolesAdapter = ArrayAdapter.createFromResource(this, R.array.roles_array,
               android.R.layout.simple_spinner_item);
        rolesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(rolesAdapter);

        permissionSpinner = (Spinner) findViewById(R.id.permissionSpinner);
        ArrayAdapter<CharSequence> permissionsAdapter = ArrayAdapter.createFromResource(this, R.array.permissions_array,
                android.R.layout.simple_spinner_item);
        permissionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        permissionSpinner.setAdapter(permissionsAdapter);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String userJson = intent.getStringExtra("user");
        if (userJson != null) {
            currentUser = new User(new JSONObject(userJson));
            setUsernameAsString(currentUser.getUsername());
            setEmailAsString(currentUser.getEmail());
            setFirstnameAsString(currentUser.getFirstName());
            setSurnameAsString(currentUser.getLastName());
            phoneEdit.setText(Long.toString(currentUser.getPhone()));
            setRoleAsString(currentUser.getRole());
            setPermissionAsString(currentUser.getPermission());
        }

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

        if(controller == null)
            controller = new AddUserController(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save_new_user) {
            if (currentUser != null)
                Cache.getInstance().put("user", currentUser);
            controller.addUser();
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_discard_user) {
            if (currentUser != null) {
                controller.deleteUser(currentUser);
                // Ask for confirmation
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, AdminActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public String getUsernameAsString() {
        return usernameEdit.getText().toString();
    }

    @Override
    public void setUsernameAsString(String value) {
        usernameEdit.setText(value);
    }

    @Override
    public void setErrorForUsername(String error) {

    }

    @Override
    public String getSurnameAsString() {
        return lastNameEdit.getText().toString();
    }

    @Override
    public void setSurnameAsString(String value) {
        lastNameEdit.setText(value);
    }

    @Override
    public void setErrorForSurname(String error) {

    }

    @Override
    public String getFirstnameAsString() {
        return firstNameEdit.getText().toString();
    }

    @Override
    public void setFirstnameAsString(String value) {
        firstNameEdit.setText(value);
    }

    @Override
    public void setErrorForFirstname(String error) {

    }

    @Override
    public String getEmailAsString() {
        return emailEdit.getText().toString();
    }

    @Override
    public void setEmailAsString(String value) {
        emailEdit.setText(value);
    }

    @Override
    public long getPhoneAsLong() {
        return Long.parseLong(phoneEdit.getText().toString());
    }

    @Override
    public void setPhoneAsLong(long value) {
        phoneEdit.setText(value + "");
    }

    @Override
    public String getRoleAsString() {
        return roleSpinner.getSelectedItem().toString();
    }

    @Override
    public void setRoleAsString(String value) {
        TypedArray ta = getResources().obtainTypedArray(R.array.roles_array);
        for(int i = 0; i < ta.length(); i++){
            if(ta.getString(i).equals(value))
                roleSpinner.setSelection(i);
        }
    }

    @Override
    public String getPasswordAsString() {
        return passwordEdit.getText().toString();
    }

    @Override
    public void setPasswordAsString(String value) {
        passwordEdit.setText(value);
    }

    @Override
    public void setErrorForPassword(String error) {

    }

    @Override
    public String getPermissionAsString() {
        return permissionSpinner.getSelectedItem().toString();
    }

    @Override
    public void setPermissionAsString(String value) {
        TypedArray ta = getResources().obtainTypedArray(R.array.permissions_array);
        for(int i = 0; i < ta.length(); i++){
            if(ta.getString(i).equals(value))
                permissionSpinner.setSelection(i);
        }
    }

    @Override
    public void setPermissionForSurname(String error) {

    }
}
