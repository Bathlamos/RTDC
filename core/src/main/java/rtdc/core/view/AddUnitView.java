package rtdc.core.view;

import rtdc.core.impl.UiElement;

public interface AddUnitView extends Dialog {
    UiElement<String> getNameUiElement();

    UiElement<String> getTotalBedsUiElement();

    void hideDeleteButton();

}
