package rtdc.android.impl;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import rtdc.core.impl.UiElement;

import java.util.Calendar;
import java.util.Date;

public class AndroidUiDate extends EditText implements UiElement<Date>, View.OnFocusChangeListener, View.OnClickListener {

    private Date date = new Date();
    private final Context context;

    public AndroidUiDate(Context context) {
        super(context);
        this.context = context;
        setOnFocusChangeListener(this);
    }

    public AndroidUiDate(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOnFocusChangeListener(this);
    }

    public AndroidUiDate(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        this.context = context;
        setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus) {
            showTimePicker();
        }
    }

    @Override
    public void onClick(View v) {
        showTimePicker();
    }

    @Override
    public Date getValue() {
        return date;
    }

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
        timePicker.setTitle("Select Time");
        timePicker.show();
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
}
