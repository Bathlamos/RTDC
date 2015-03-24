package rtdc.android.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import rtdc.core.impl.TextValidationWidget;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AndroidTextValidationWidget extends EditText implements TextValidationWidget {

    public AndroidTextValidationWidget(Context context) {
        super(context);
    }

    public AndroidTextValidationWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidTextValidationWidget(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // Get EditText value
    @Override
    public String getInputText() {
        return String.valueOf(super.getText());
    }

    // Set EditText value
    @Override
    public void setInputText(String text) {
        super.setText(text);
    }

    // Check is EditText is empty
    @Override
    public boolean isEmpty() {
        return (this.getInputText().equals(""));
    }

    // Get EditText error
    @Override
    public String getError() {
        return String.valueOf(super.getError());
    }

    // Set EditText error
    @Override
    public void setError(String error) {
        super.setError(error);
    }

    // Clear EditText error
    @Override
    public void clearError() {
        super.setError(null);
    }

}