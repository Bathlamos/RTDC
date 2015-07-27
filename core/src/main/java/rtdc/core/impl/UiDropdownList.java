package rtdc.core.impl;

import rtdc.core.util.Stringifier;

import java.util.List;

public interface UiDropdownList<T> extends UiElement<T> {

    Stringifier DEFAULT_STRINGIFIER = new Stringifier() {
        @Override
        public String toString(Object object) {
            return object.toString();
        }
    };

    void setList(List<T> elements);

    int getSelectedIndex();

    void setStringifier(Stringifier<T> stringifier);

}
