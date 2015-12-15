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

package rtdc.core.impl;

/**
 * An abstraction of a UI component that (1) displays a value, (2) allows that value to be edited and
 * (3) shows errors in the UI when the value is incorrect.
 * @param <T> The type of the value displayed in the UI component
 */
public interface UiElement<T> {

    /**
     * @return The value currently displayed
     */
    T getValue();

    /**
     * Sets the value to be displayed
     * @param value The value to display
     */
    void setValue(T value);

    /**
     * @return An empty String, or null if there are no errors. Otherwise, the error shown.
     */
    String getErrorMessage();

    /**
     * Sets the error message for this UI component. This will erase previous error messages.
     * @param errorMessage The error message to be shown
     */
    void setErrorMessage(String errorMessage);

    /**
     * Calls for focus to be given to this element. This results in the appearance of a keyboard, in Android,
     * and in the focus of the cursor on the element, in GWT.
     * @param hasFocus Focus will be set, if true, and removed, if false
     */
    void setFocus(boolean hasFocus);

}
