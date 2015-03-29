package rtdc.core.view;

public interface BootstrapperView {

    void saveAuthenticationToken(String authToken);
    String getAuthenticationToken();
}