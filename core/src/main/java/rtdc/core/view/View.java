package rtdc.core.view;

public interface View {

    void displayError(String title, String error);

    void clearError();

    void setTitle(String title);

}
