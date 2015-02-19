package rtdc.android.impl;

import android.content.Context;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import rtdc.android.Rtdc;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONException;
import rtdc.core.service.AsyncCallback;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AndroidHttpRequest implements HttpRequest {

    private static RequestQueue mRequestQueue;

    private String url;
    private int requestMethod;
    private Map<String, String> params = new HashMap<String, String>(),
        headers = new HashMap<String, String>();

    public AndroidHttpRequest(String url, HttpRequest.RequestMethod requestMethod){
        this.url = url;
        this.requestMethod = 0; // Defaults to GET
        switch(requestMethod){
            case GET: this.requestMethod = Request.Method.GET; break;
            case POST: this.requestMethod = Request.Method.POST; break;
            case PUT: this.requestMethod = Request.Method.PUT; break;
            case DELETE: this.requestMethod = Request.Method.DELETE; break;
        }
    }

    @Override
    public void addParameter(String parameter, String data) {
        params.put(parameter, data);
    }

    @Override
    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public void execute(final AsyncCallback<HttpResponse> callback) {
        Response.Listener listener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                callback.onSuccess(new AndroidHttpResponse(200, response));
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.networkResponse.toString() + " : " + error.getMessage());
            }
        };

        //Format the parameters
        List<BasicNameValuePair> paramsAsValuePairs = new LinkedList<BasicNameValuePair>();
        for(Map.Entry<String, String> param: params.entrySet())
            paramsAsValuePairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));

        getRequestQueue().add(new JsonObjectRequest(requestMethod, url, URLEncodedUtils.format(paramsAsValuePairs, "UTF-8"), listener, errorListener));
    }

    public static RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(Rtdc.getAppContext().getApplicationContext());
        }
        return mRequestQueue;
    }

    private final class JsonObjectRequest extends JsonRequest<String> {

        public JsonObjectRequest(int method, String url, String requestBody, Response.Listener<String> listener,
                                 Response.ErrorListener errorListener) {
            super(method, url, requestBody, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                return Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JSONException je) {
                return Response.error(new ParseError(je));
            }
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return params;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            return headers;
        }
    }
}