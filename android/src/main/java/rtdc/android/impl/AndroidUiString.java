package rtdc.android.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import rtdc.core.impl.UiElement;

public class AndroidUiString extends EditText implements UiElement<String> {

    public AndroidUiString(Context context) {
        super(context, null);
    }

    public AndroidUiString(Context context, AttributeSet attrs) {
        super(context, attrs, com.android.internal.R.attr.editTextStyle);
    }

    @Override
    public String getValue() {
        return getText().toString();
    }

    @Override
    public void setValue(String value) {
        setText(value);
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
