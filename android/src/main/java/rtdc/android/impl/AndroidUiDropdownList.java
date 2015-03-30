package rtdc.android.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.common.collect.ImmutableSet;
import rtdc.android.R;
import rtdc.core.impl.UiDropdownList;

import java.util.ArrayList;
import java.util.List;

public class AndroidUiDropdownList<T> extends Spinner implements UiDropdownList<T> {

    private final ArrayAdapter<T> adapter;

    public AndroidUiDropdownList(Context context) {
        super(context);

        adapter = new ArrayAdapter<T>(context, R.layout.downdown_list_item);
        setAdapter(adapter);
    }

    public AndroidUiDropdownList(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new ArrayAdapter<T>(context, R.layout.downdown_list_item);
        setAdapter(adapter);
    }

    public AndroidUiDropdownList(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        adapter = new ArrayAdapter<T>(context, R.layout.downdown_list_item);
        setAdapter(adapter);
    }

    @Override
    public int getSelectedIndex(){
        return getSelectedItemPosition();
    }

    @Override
    public T getValue() {
        return adapter.getItem(getSelectedItemPosition());
    }

    @Override
    public void setList(List<T> elements) {
        adapter.clear();
        adapter.addAll(elements);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setValue(T value) {
        int position = adapter.getPosition(value);

        if(position != -1)
            setSelection(position);
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
