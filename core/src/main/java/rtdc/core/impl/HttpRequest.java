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

import rtdc.core.service.AsyncCallback;

/**
 * An abstraction of an http request.
 */
public interface HttpRequest {

    /**
     * Enumeration of popular HTTP verbs. Some are omitted, but may be added if necessary.
     */
    enum RequestMethod{GET, PUT, DELETE, POST}

    /**
     * Adds a parameters to the request. If the HTTP verb is a POST, then the data is encapsulated in the request body.
     * If the request is a GET, then the data is appended to the URL (and url form-encoded)
     * @param parameter The name of the parameter
     * @param data The value of the parameter
     */
    void addParameter(String parameter, String data);

    /**
     * Appends a new header to the HTTP request
     * @param name The name of the header
     * @param value The value of the header
     */
    void setHeader(String name, String value);

    /**
     * Asynchronously execute the request.
     * @param response A listener, called when the request fails, times out, or succeeds.
     */
    void execute(AsyncCallback<HttpResponse> response);

    /**
     * Change the content type of the HTTP request
     * @param contentType The new content type.
     */
    void setContentType(String contentType);

}
