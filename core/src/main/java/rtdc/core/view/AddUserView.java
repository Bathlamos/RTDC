package rtdc.core.view;

public interface AddUserView extends View {

    String getUsernameAsString();
    void setUsernameAsString(String username);
    void setErrorForUsername(String error);

    String getSurnameAsString();
    void setSurnameAsString(String username);
    void setErrorForSurname(String error);

    String getFirstnameAsString();
    void setFirstnameAsString(String username);
    void setErrorForFirstname(String error);

    String getFirstPasswordAsString();
    void setFirstPasswordAsString(String username);
    void setErrorForFirstPassword(String error);

    String getSecondPasswordAsString();
    void setSecondPasswordAsString(String username);
    void setErrorForSecondPassword(String error);

    String getPermissionAsString();
    void setPermissionAsString(String username);
    void setPermissionForSurname(String error);

}
