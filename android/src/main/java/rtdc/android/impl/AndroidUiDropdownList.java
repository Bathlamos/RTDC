package rtdc.android.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.common.collect.ImmutableSet;
import rtdc.core.impl.UiDropdownList;

import java.util.ArrayList;

public class AndroidUiDropdownList<T> extends Spinner implements UiDropdownList<T> {

    private final ArrayAdapter<T> adapter;
    private final ArrayList<T> data = new ArrayList<T>();

    public AndroidUiDropdownList(Context context) {
        this(context, null);
    }

    public AndroidUiDropdownList(Context context, AttributeSet attrs) {
        super(context, attrs, com.android.internal.R.attr.editTextStyle);
        adapter = new ArrayAdapter<T>(context, android.R.layout.simple_spinner_item, data);
        setAdapter(adapter);
    }

    @Override
    public T getValue() {
        return data.get(getSelectedItemPosition());
    }

    @Override
    public void setList(ImmutableSet<T> elements) {
        data.clear();
        data.addAll(elements);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setValue(T value) {
        for(int i = 0; i < data.size(); i++)
            if(data.get(i) == value) {
                setSelection(i);
                break;
            }
    }

    @Override
    public String getErrorMessage() {
        return "";
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        //Does nothing
    }

}
