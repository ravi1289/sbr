package com.flipkart.studio34;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.lang.reflect.Type;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class PodcastRequest<T> extends Request<T> {

    Type type;
    protected Response.Listener responseListener;
    public PodcastRequest(String url, Response.ErrorListener errorListener, Response.Listener responseListener, Type carResponseType){
        super(Method.GET, url, errorListener);
        this.type= carResponseType;
        this.responseListener = responseListener;
    }
    @Override
    public Response<T> parseNetworkResponse(NetworkResponse response) {
        if(response.statusCode == 200){
            String jsonResponse = new String(response.data);
            T podcastResponse = JsonUtils.getInstance().getGson().fromJson(jsonResponse,type);
            return Response.success(podcastResponse, HttpHeaderParser.parseCacheHeaders(response));
        }
        return Response.error(new VolleyError(response));
    }

    @Override
    public void deliverResponse(T response) {
        responseListener.onResponse(response);
    }
}

