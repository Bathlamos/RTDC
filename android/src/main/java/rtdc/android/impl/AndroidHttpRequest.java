package rtdc.android.impl;

import android.content.Context;
import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import rtdc.android.Rtdc;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.json.JSONException;
import rtdc.core.service.AsyncCallback;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AndroidHttpRequest implements HttpRequest {

    private String url;
    private int requestMethod;
    private Map<String, String> params = new HashMap<>(),
        headers = new HashMap<>();

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
        Context context = Rtdc.getAppContext();
        RequestQueue requestQueue = MyVolley.getInstance(context).getRequestQueue();

        Response.Listener listener = new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                callback.onSuccess(new AndroidHttpResponse(400, response));
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onError(error.getMessage());
            }
        };

        requestQueue.add(new JsonObjectRequest(requestMethod, url, params.toString(), listener, errorListener));
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
    }
}