package rtdc.web.client.impl;

import com.google.gwt.user.client.ui.ListBox;
import rtdc.core.impl.UiDropdown;
import rtdc.core.util.Stringifier;

import java.util.ArrayList;
import java.util.Arrays;

public class GwtUiDropdown<T> extends ListBox implements UiDropdown<T> {

    private Stringifier<T> stringifier = DEFAULT_STRINGIFIER;
    ArrayList<T> items;


    @Override
    public void setArray(T[] elements) {
        items = new ArrayList<>(Arrays.asList(elements));
        for (T element : items) {
            addItem(element.toString());
        }
    }

    @Override
    public void setStringifier(Stringifier<T> stringifier) {
        this.stringifier = stringifier;
    }

    @Override
    public T getValue() {
        return items.get(getSelectedIndex());
    }

    @Override
    public void setValue(T value) {
        setSelectedIndex(items.indexOf(value));
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }
}
