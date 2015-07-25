package rtdc.core.view;

import com.google.common.collect.ImmutableSet;
import rtdc.core.impl.UiDropdownList;
import rtdc.core.impl.UiElement;
import rtdc.core.model.Action;

import java.util.Date;

public interface AddActionView extends View {

    UiDropdownList<String> getUnitUiElement();

    UiDropdownList<Action.Status> getStatusUiElement();

    UiElement<String> getRoleUiElement();

    UiDropdownList<String> getTaskUiElement();

    UiElement<String> getTargetUiElement();

    UiElement<Date> getDeadlineUiElement();

    UiElement<String> getDescriptionUiElement();
}
