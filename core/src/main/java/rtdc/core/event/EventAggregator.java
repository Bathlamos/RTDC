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

package rtdc.core.event;

import com.google.common.collect.ImmutableSet;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

public class EventAggregator<T> {

    final HashSet<WeakReference<T>> handlers = new HashSet<WeakReference<T>>();

    public void addHandler(T object){
        if(object != null)
            handlers.add(new WeakReference<T>(object));
    }

    public void removeHandler(T object){
        if(object != null){
            Iterator<WeakReference<T>> it = handlers.iterator();
            while(it.hasNext()){
                T next = it.next().get();
                if(next == null || next == object)
                    it.remove();
            }
        }
    }

    //Clear at the same time
    public ImmutableSet<T> getHandlers(){
        ImmutableSet.Builder<T> builder = ImmutableSet.builder();
        Iterator<WeakReference<T>> it = handlers.iterator();
        while(it.hasNext()){
            T next = it.next().get();
            if(next == null)
                it.remove();
            else
                builder.add(next);
        }
        return builder.build();
    }

}
