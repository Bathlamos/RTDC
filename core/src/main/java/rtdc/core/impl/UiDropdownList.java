package rtdc.core.impl;

import com.google.common.collect.ImmutableSet;

import java.util.List;

public interface UiDropdownList<T> extends UiElement<T> {

    public void setList(List<T> elements);

    public int getSelectedIndex();

}
