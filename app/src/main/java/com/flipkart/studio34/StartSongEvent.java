package com.flipkart.studio34;

/**
 * Created by ravi.krishnan on 25/02/16.
 */
public class StartSongEvent {
    private String url;

    public StartSongEvent(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
