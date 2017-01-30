package com.intrvw.haptiksampleapp.model;


import java.util.ArrayList;

/**
 * Created by rainiksoni on 28/01/17.
 */

public class User {

    private String name;

    private String avatarUrl;


    private int numberOfMessages = 0;

    private int numberOfStarredMessages = 0;


    public User(String name, String avatarUrl){
        this.name = name;
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }



    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getNumberOfMessages() {
        return numberOfMessages;
    }

    public void setNumberOfMessages(int numberOfMessages) {
        this.numberOfMessages = numberOfMessages;
    }

    public int getNumberOfStarredMessages() {
        return numberOfStarredMessages;
    }

    public void setNumberOfStarredMessages(int numberOfStarredMessages) {
        this.numberOfStarredMessages = numberOfStarredMessages;
    }
}
