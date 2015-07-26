package rtdc.core.view;

public interface AddUnitView extends Dialog {

    String getNameAsString();
    void setNameAsString(String name);

    String getTotalBedsAsString();
    void setTotalBedsAsString(String totalBeds);

    void hideDeleteButton();

}
