package rtdc.core.impl;

import com.google.common.collect.ImmutableSet;

public interface UiDropdownList<T> extends UiElement<T> {

    public void setList(ImmutableSet<T> elements);

}
