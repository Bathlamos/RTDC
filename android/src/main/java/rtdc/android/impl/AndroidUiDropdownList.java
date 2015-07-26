package rtdc.android.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.common.collect.ImmutableSet;
import rtdc.android.R;
import rtdc.core.impl.UiDropdownList;
import rtdc.core.util.Stringifier;

import java.util.ArrayList;
import java.util.List;

public class AndroidUiDropdownList<T> extends Spinner implements UiDropdownList<T> {

    private final CustomAdapter adapter;
    private Stringifier<T> stringifier = DEFAULT_STRINGIFIER;

    public AndroidUiDropdownList(Context context) {
        super(context);

        adapter = new CustomAdapter(context, R.layout.downdown_list_item);
        setAdapter(adapter);
    }

    public AndroidUiDropdownList(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new CustomAdapter(context, R.layout.downdown_list_item);
        setAdapter(adapter);
    }

    public AndroidUiDropdownList(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        adapter = new CustomAdapter(context, R.layout.downdown_list_item);
        setAdapter(adapter);
    }

    @Override
    public int getSelectedIndex(){
        return getSelectedItemPosition();
    }

    @Override
    public void setStringifier(Stringifier<T> stringifier) {
        this.stringifier = stringifier;
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

    @Override
    public void setFocus(boolean hasFocus) {
        if(hasFocus)
            requestFocus();
    }

    private class CustomAdapter extends ArrayAdapter<T> {

        public CustomAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(this.getContext())
                        .inflate(R.layout.downdown_list_item, parent, false);

            T item = getItem(position);
            TextView textView = (TextView) convertView;
            if (item!= null)
                textView.setText(stringifier.toString(item));

            return convertView;
        }
    }

}
