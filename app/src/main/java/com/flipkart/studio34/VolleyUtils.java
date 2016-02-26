package com.flipkart.studio34;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class VolleyUtils {
    private RequestQueue requestQueue;
    private static Context context;


    private static VolleyUtils volleyUtils;

    private VolleyUtils(Context context){
        VolleyUtils.context=context;
        requestQueue = getRequestQueue();
    }

    public static VolleyUtils getInstance(Context context) {
        if (volleyUtils == null){
            synchronized (VolleyUtils.class) {
                if(volleyUtils ==null) {
                    volleyUtils = new VolleyUtils(context);
                }
            }
        }
        return volleyUtils;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue==null) {
            requestQueue= Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addRequest(Request<T> request ){
        requestQueue.add(request);
    }
}