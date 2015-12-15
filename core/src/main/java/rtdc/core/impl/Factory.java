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
 * Factory class to instantiate platform dependant classes.
 * The class is implemented on each platform so that the underlying functionality
 * may be used in the core.
 */
public interface Factory {

    /**
     * Generate a new async HTTP request
     * @param url A complete URL of the resource to be accessed
     * @param requestMethod One of the HTTP verb
     * @return A new HttpRequest object, which may be used to execute an async HTTP request.
     */
    HttpRequest newHttpRequest(String url, HttpRequest.RequestMethod requestMethod);

    /**
     * Create a new dispatcher to navigate the application
     * @return A new instance of Dispatcher
     */
    Dispatcher newDispatcher();

    /**
     * Accesses an abstraction of a persistence mechanism.
     * @return The unique instance of Storage
     */
    Storage getStorage();

    /**
     * Accesses an abstraction of the VoIP mechanism.
     * @return The unique instance of VoipController
     */
    VoipController getVoipController();

}
