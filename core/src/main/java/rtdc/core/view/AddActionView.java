package rtdc.core.view;

import com.google.common.collect.ImmutableSet;
import rtdc.core.impl.UiDropdownList;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Action;
import rtdc.core.model.Unit;

import java.util.Date;

public interface AddActionView extends Dialog {

    UiDropdownList<Unit> getUnitUiElement();

    UiDropdownList<Action.Status> getStatusUiElement();

    UiElement<String> getRoleUiElement();

    UiDropdownList<Action.Task> getTaskUiElement();

    UiElement<String> getTargetUiElement();

    UiElement<Date> getDeadlineUiElement();

    UiElement<String> getDescriptionUiElement();
}
