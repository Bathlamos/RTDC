package rtdc.android.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import rtdc.android.R;
import rtdc.core.impl.UiDropdown;
import rtdc.core.util.Stringifier;

import java.util.Arrays;
import java.util.List;

public class AndroidUiDropdown<T> extends Spinner implements UiDropdown<T> {

    private final CustomAdapter adapter;
    private Stringifier<T> stringifier = DEFAULT_STRINGIFIER;

    public AndroidUiDropdown(Context context) {
        super(context);

        adapter = new CustomAdapter(context, R.layout.dropdown_list_item);
        setAdapter(adapter);
    }

    public AndroidUiDropdown(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new CustomAdapter(context, R.layout.dropdown_list_item);
        setAdapter(adapter);
    }

    public AndroidUiDropdown(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        adapter = new CustomAdapter(context, R.layout.dropdown_list_item);
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
    public void setArray(T[] elements) {
        adapter.clear();
        adapter.addAll(Arrays.asList(elements));
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
                        .inflate(R.layout.dropdown_selected_item, parent, false);
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(this.getContext())
                        .inflate(R.layout.dropdown_list_item, parent, false);
            return getCustomView(position, convertView, parent);
        }

        private View getCustomView(int position, View convertView, ViewGroup parent){


            T item = getItem(position);
            TextView textView = (TextView) convertView.findViewById(R.id.dropDownText);

            String name = stringifier.toString(item);

            if (item!= null)
                textView.setText(name);

            return convertView;
        }

    }

}
