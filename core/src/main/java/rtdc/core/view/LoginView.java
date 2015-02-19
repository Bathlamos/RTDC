package rtdc.core.view;

public interface LoginView extends View {

    String getUsername();
    void setUsername(String username);

    String getPassword();
    void setPassword(String password);

    void saveAuthenticationToken(String token);

}
