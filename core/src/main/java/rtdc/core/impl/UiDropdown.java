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

import rtdc.core.util.Stringifier;

import java.util.List;

/**
 * An abstraction of a generic dropdown ui component.
 * @param <T> The type of the elements displayed in the dropdown
 */
public interface UiDropdown<T> extends UiElement<T> {

    // Used to translate arbitrary objects into Strings, for display
    // when no other Stringifier is given
    Stringifier DEFAULT_STRINGIFIER = new Stringifier() {
        @Override
        public String toString(Object object) {
            return object.toString();
        }
    };

    /**
     * Sets the elements to be displayed. The order is preserved.
     * @param elements Non-null elements to be displayed.
     */
    void setArray(T[] elements);

    /**
     * @return The index of the elements that us currently selected, in the original array.
     */
    int getSelectedIndex();

    /**
     * The method used to translate the object into a display-ready String.
     * For instance, a Unit object might have a <code>Stringifier</code> calling <code>unit.getName()</code>
     * @param stringifier The Stringifier to be used
     */
    void setStringifier(Stringifier<T> stringifier);

}
