package rtdc.core.view;

public interface BootstrapperView extends View {

    boolean hasAuthenticationToken();
    String getAuthenticationToken();

    void goToLogin();
    void goToMain();

}
