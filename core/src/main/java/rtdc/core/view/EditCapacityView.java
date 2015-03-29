package rtdc.core.view;


import rtdc.core.impl.UiElement;

public interface EditCapacityView extends View {

    UiElement<String> getAvailableBedsUiElement();

    UiElement<String> getPotentialDcUiElement();

    UiElement<String> getDcByDeadlineUiElement();

    UiElement<String> getTotalAdmitsUiElement();

    UiElement<String> getAdmitsByDeadlineUiElement();

}
