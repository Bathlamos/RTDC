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

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import rtdc.core.i18n.ResBundle;
import rtdc.core.impl.UiElement;

import java.util.Calendar;
import java.util.Date;

/**
 * Android abstraction of a date picker
 */
public class AndroidUiDate extends EditText implements UiElement<Date>, View.OnFocusChangeListener, View.OnClickListener {

    private Date date = new Date();
    private final Context context;

    public AndroidUiDate(Context context) {
        super(context);
        this.context = context;
        setOnFocusChangeListener(this);
        setOnClickListener(this);
    }

    public AndroidUiDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOnFocusChangeListener(this);
        setOnClickListener(this);
    }

    public AndroidUiDate(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        this.context = context;
        setOnFocusChangeListener(this);
        setOnClickListener(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            showTimePicker();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onClick(View v) {
        showTimePicker();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date getValue() {
        return date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Date value) {
        this.date = value;
        setText(date.getHours() + ":" + String.format("%02d", date.getMinutes()));
    }

    public void showTimePicker() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                Date date = new Date();
                date.setHours(selectedHour);
                date.setMinutes(selectedMinute);
                setValue(date);
            }
        }, hour, minute, true);
        timePicker.setTitle(ResBundle.get().selectTime());
        timePicker.show();
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
}
