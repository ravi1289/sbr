package com.flipkart.studio34;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class JsonUtils {
    private static JsonUtils jsonUtils = new JsonUtils();
    private Gson gson;

    public static JsonUtils getInstance() {
        return jsonUtils;
    }

    public JsonUtils() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        this.gson = gsonBuilder.create();
    }

    public Gson getGson() {
        return this.gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
