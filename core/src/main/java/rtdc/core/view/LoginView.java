package rtdc.core.view;

public interface LoginView {

    void displayError(String error);

    String getUsername();
    void setUsername(String username);

    String getPassword();
    String setPassword(String password);

}
