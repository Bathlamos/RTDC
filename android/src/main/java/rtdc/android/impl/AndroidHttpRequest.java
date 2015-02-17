package rtdc.android.impl;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import rtdc.android.MyApplication;
import rtdc.core.impl.HttpRequest;
import rtdc.core.impl.HttpResponse;
import rtdc.core.service.AsyncCallback;
import rtdc.core.json.JSONObject;

public class AndroidHttpRequest implements HttpRequest {

    private String url;
    private RequestMethod requestMethod;
    private String requestData;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    @Override
    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    @Override
    public void execute(final AsyncCallback<HttpResponse> callback) {
        Context context = MyApplication.getAppContext();

        RequestQueue requestQueue = MyVolley.getInstance(context).getRequestQueue();
        int rMethod = 0; // Defaults to GET
        switch(requestMethod){
            case GET: rMethod = Request.Method.GET; break;
            case POST: rMethod = Request.Method.POST; break;
            case PUT: rMethod = Request.Method.PUT; break;
            case DELETE: rMethod = Request.Method.DELETE; break;
        }

        JsonObjectRequest request = new JsonObjectRequest(rMethod, url, requestData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // TODO Extract actual response code from response.
                callback.onCallback(new AndroidHttpResponse(400, response.toString()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Log Error or display it in application
            }
        });

        requestQueue.add(request);
    }
}
