/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import java.util.logging.Logger;

/**
 * Android abstraction of a dropdown box
 */
public class AndroidUiDropdown<T> extends Spinner implements UiDropdown<T> {

    private final CustomAdapter adapter;
    private T[] dropDownValues;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedIndex(){
        return getSelectedItemPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStringifier(Stringifier<T> stringifier) {
        this.stringifier = stringifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() {
        return adapter.getItem(getSelectedItemPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setArray(T[] elements) {
        adapter.clear();
        adapter.addAll(Arrays.asList(elements));
        adapter.notifyDataSetChanged();
        dropDownValues = elements;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(T value) {
        int position = adapter.getPosition(value);

        if(position != -1) {
            for (int i = 0; i < dropDownValues.length; i++) {
                T dropDownValue = dropDownValues[i];
                if (stringifier.toString(dropDownValue).equals(stringifier.toString(value))) {
                    position = adapter.getPosition(value);
                }
            }
        }

        if(position != -1)
            setSelection(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getErrorMessage() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setErrorMessage(String errorMessage) {
        //Does nothing
    }

    /**
     * {@inheritDoc}
     */
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
