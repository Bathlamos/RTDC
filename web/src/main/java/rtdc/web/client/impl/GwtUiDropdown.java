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

package rtdc.web.client.impl;

import com.google.gwt.user.client.ui.ListBox;
import rtdc.core.impl.UiDropdown;
import rtdc.core.util.Stringifier;

import java.util.ArrayList;
import java.util.Arrays;

public class GwtUiDropdown<T> extends ListBox implements UiDropdown<T> {

    private Stringifier<T> stringifier = DEFAULT_STRINGIFIER;
    ArrayList<T> items;


    @Override
    public void setArray(T[] elements) {
        items = new ArrayList<>(Arrays.asList(elements));
        for (T element : items) {
            addItem(element.toString());
        }
    }

    @Override
    public void setStringifier(Stringifier<T> stringifier) {
        this.stringifier = stringifier;
    }

    @Override
    public T getValue() {
        return items.get(getSelectedIndex());
    }

    @Override
    public void setValue(T value) {
        setSelectedIndex(items.indexOf(value));
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Override
    public void setErrorMessage(String errorMessage) {

    }
}
