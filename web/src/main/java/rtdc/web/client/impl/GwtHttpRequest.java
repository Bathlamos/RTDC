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

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.service.AsyncCallback;

/**
 * GWT abstraction of an XMLHttpRequest
 */
public class GwtHttpRequest implements HttpRequest{

    private final RequestBuilder builder;
    private StringBuilder params = new StringBuilder();

    public GwtHttpRequest(String url, RequestMethod requestMethod){
        RequestBuilder.Method method = null;
        switch(requestMethod){
            case GET: method = RequestBuilder.GET; break;
            case POST: method = RequestBuilder.POST; break;
            case PUT: method = RequestBuilder.PUT; break;
            case DELETE: method = RequestBuilder.DELETE; break;
        }
        builder = new RequestBuilder(method, url);
    }

    public void setHeader(String name, String value){
        builder.setHeader(name, value);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void addParameter(String parameter, String data) {
        if(params.length() != 0)
            params.append('&');
        params.append(parameter);
        params.append('=');
        params.append(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final AsyncCallback<HttpResponse> callback) {
        try {
            builder.sendRequest(params.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    callback.onSuccess(new GwtHttpResponse(response));
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onError(exception.getMessage());
                }
            });
        }catch(RequestException exception){
            exception.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setContentType(String contentType) {
        builder.setHeader("Content-Type", contentType);
    }
}
