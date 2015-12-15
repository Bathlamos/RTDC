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

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import rtdc.android.AndroidBootstrapper;
import rtdc.core.impl.Storage;

import java.util.logging.Logger;

/**
 * Android abstraction of a persistence mechanism (SharedPreferences)
 */
public class AndroidStorage implements Storage
{
    private static final Logger LOG = Logger.getLogger(AndroidStorage.class.getSimpleName());
    private static SharedPreferences settings;
    private static AndroidStorage INSTANCE = null;

    private AndroidStorage(){
        settings = PreferenceManager.getDefaultSharedPreferences(AndroidBootstrapper.getAppContext());
    }

    public static AndroidStorage get(){
        if(INSTANCE == null)
            INSTANCE = new AndroidStorage();
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void add(String key, String data) {
        LOG.info("Adding " + key + ":" + data);
        settings.edit().putString(key, data).commit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String retrieve(String key) {
        LOG.info("Retrieving " + key + ":" + settings.getString(key, ""));
        return settings.getString(key, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(String key) {
        settings.edit().remove(key).commit();
    }
}
