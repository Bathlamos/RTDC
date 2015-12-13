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
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import rtdc.core.impl.UiElement;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
    This class contains a fix for Android JellyBean 4.2 and 4.2.1
    In those versions of Android, the error icon of EditText fields are not displayed when they should
    Fix was taken from here: stackoverflow.com/questions/13756978/android-os-bug-with-some-devices-running-jelly-bean-4-2-1-textview-seterrorch/14175640#14175640
 */

public class AndroidUiString extends EditText implements UiElement<String> {

    // Keep track of which error icon we used last
    private Drawable lastErrorIcon = null;

    public AndroidUiString(Context context) {
        super(context);
    }

    public AndroidUiString(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AndroidUiString(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
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
        if(getError() == null)
            return null;
        return getError().toString();
    }

    @Override
    public void setErrorMessage(String errorMessage) {
        setError(errorMessage);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        super.setError(error, icon);
        lastErrorIcon = icon;

        // if the error is not null, and we are in JB, force the error to show
        if (error != null)
            showErrorIconHax(icon);
    }

    @Override
    public void setFocus(boolean hasFocus) {
        if(hasFocus)
            requestFocus();
    }

    /**
     * Don't send delete key so edit text doesn't capture it and close error
     */
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (TextUtils.isEmpty(getText().toString()) && keyCode == KeyEvent.KEYCODE_DEL)
            return true;
        else
            return super.onKeyPreIme(keyCode, event);
    }

    /**
     * In onFocusChanged() we also have to reshow the error icon as the Editor
     * hides it. Because Editor is a hidden class we need to cache the last used
     * icon and use that
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        showErrorIconHax(lastErrorIcon);
    }

    /**
     * Use reflection to force the error icon to show. Dirty but resolves the
     * issue in 4.2
     */
    private void showErrorIconHax(Drawable icon) {
        if (icon == null)
            return;

        // only for JB 4.2 and 4.2.1
        if (android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.JELLY_BEAN &&
                android.os.Build.VERSION.SDK_INT != Build.VERSION_CODES.JELLY_BEAN_MR1)
            return;

        try {
            Class<?> textview = Class.forName("android.widget.TextView");
            Field tEditor = textview.getDeclaredField("mEditor");
            tEditor.setAccessible(true);
            Class<?> editor = Class.forName("android.widget.Editor");
            Method privateShowError = editor.getDeclaredMethod("setErrorIcon", Drawable.class);
            privateShowError.setAccessible(true);
            privateShowError.invoke(tEditor.get(this), icon);
        } catch (Exception e) {
            // e.printStackTrace(); // oh well, we tried
        }
    }
}
