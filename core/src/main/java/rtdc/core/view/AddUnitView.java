package rtdc.core.view;

public interface AddUnitView extends View {

    String getNameAsString();
    void setNameAsString(String name);

    String getTotalBedsAsString();
    void setTotalBedsAsString(String totalBeds);

    void hideDeleteButton();

}
