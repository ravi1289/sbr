package com.flipkart.studio34;

import java.util.List;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class Media {

    private int id;
    private String name;
    private String url;
    private List<MediaMetaData> mediaMetaDataList;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<MediaMetaData> getMediaMetaDataList() {
        return mediaMetaDataList;
    }
}
