package com.flipkart.studio34;

import java.util.List;

/**
 * Created by ravi.krishnan on 26/02/16.
 */
public class PodcastInfo {


    private Media media;

    private List<FeedbackComment> feedbackCommentList;
    private int likeCount ;
    private int dislikeCount ;
    private int userEmotion ;

    public List<FeedbackComment> getFeedbackCommentList() {
        return feedbackCommentList;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public int getUserEmotion() {
        return userEmotion;
    }
    public Media getMedia() {
        return media;
    }
}
