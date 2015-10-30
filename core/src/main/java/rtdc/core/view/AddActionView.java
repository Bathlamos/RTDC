package rtdc.core.view;

import rtdc.core.impl.UiDropdown;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;

import java.util.Date;

public interface AddActionView extends Dialog {

    UiDropdown<Unit> getUnitUiElement();

    UiDropdown<Action.Status> getStatusUiElement();

    UiElement<String> getRoleUiElement();

    UiDropdown<Action.Task> getTaskUiElement();

    UiElement<String> getTargetUiElement();

    UiElement<Date> getDeadlineUiElement();

    UiElement<String> getDescriptionUiElement();
}
