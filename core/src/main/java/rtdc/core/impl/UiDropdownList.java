package rtdc.core.impl;

import java.util.List;

public interface UiDropdownList<T> extends UiElement<T> {

    void setList(List<T> elements);

    int getSelectedIndex();

}
