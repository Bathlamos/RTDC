package rtdc.core.view;

import rtdc.core.impl.UiElement;
import rtdc.core.model.Unit;

public interface AddUserView extends View {

    String getUsernameAsString();
    void setUsernameAsString(String value);
    void setErrorForUsername(String error);

    String getSurnameAsString();
    void setSurnameAsString(String value);
    void setErrorForSurname(String error);

    String getFirstnameAsString();
    void setFirstnameAsString(String value);
    void setErrorForFirstname(String error);

    String getEmailAsString();
    void setEmailAsString(String value);

    long getPhoneAsLong();
    void setPhoneAsLong(long value);

    String getRoleAsString();
    void setRoleAsString(String value);

    void setRoleAsString(String value);
    String getPasswordAsString();
    void setPasswordAsString(String value);

    void setErrorForPassword(String error);
    
    String getPermissionAsString();
    void setPermissionAsString(String value);
    void setPermissionForSurname(String error);

}
