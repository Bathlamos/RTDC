package rtdc.android.impl;

import rtdc.core.impl.HttpResponse;

public class AndroidHttpResponse implements HttpResponse {

    private int statusCode;
    private String content;

    public AndroidHttpResponse(int statusCode, String content){
        this.statusCode = statusCode;
        this.content = content;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getContent() {
        return content;
    }
}
