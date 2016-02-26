package com.flipkart.studio34.models;

/**
 * Created by sandesh.kumar on 25/02/16.
 */
public class User {
    String externalId;
    String name;
    String email;

    public User(String externalId, String name, String email) {
        this.externalId = externalId;
        this.name = name;
        this.email = email;
    }
}
