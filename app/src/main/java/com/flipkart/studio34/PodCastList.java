package com.flipkart.studio34;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class PodCastList {
    @SerializedName("mediaList")
    private List<Podcast> podcastList;

    public List<Podcast> getPodcastList() {
        return podcastList;
    }
}
