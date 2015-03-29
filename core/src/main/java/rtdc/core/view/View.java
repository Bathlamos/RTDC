package rtdc.core.view;

public interface View {

    void displayPermanentError(String title, String error);

    void displayError(String title, String error);

    void setTitle(String title);

}
