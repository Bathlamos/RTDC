package rtdc.android.presenter;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import rtdc.android.R;
import rtdc.core.controller.AddUserController;
import rtdc.core.view.AddUserView;

import java.lang.reflect.Field;

public class CreateUserActivity extends AbstractDialog implements AddUserView {

    private AddUserController controller;

    private EditText usernameEdit, passwordEdit, emailEdit, firstNameEdit, lastNameEdit, phoneEdit;
    private Spinner roleSpinner, permissionSpinner;
    private boolean hideDeleteButton = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        // Add the action bar at the top.
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
               controller.addUser();
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

    @Override
    public void hideDeleteButton() {
        hideDeleteButton = true;
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

    @Override
    public void onStop() {
        super.onStop();
        controller.onStop();
    }
}
