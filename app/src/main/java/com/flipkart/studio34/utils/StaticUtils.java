package com.flipkart.studio34.utils;

import com.flipkart.studio34.models.User;

/**
 * Created by sandesh.kumar on 26/02/16.
 */
public class StaticUtils {

    private static StaticUtils staticUtils = new StaticUtils();
    private StaticUtils() {

    }
    private User loggedInUser;
    public static StaticUtils getInstance() {
        return staticUtils;
    }
    private static final String TAG = "StaticUtils";

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
