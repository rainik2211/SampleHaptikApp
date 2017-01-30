package com.intrvw.haptiksampleapp.model;

/**
 * Created by rainiksoni on 28/01/17.
 */

public class Message {


    private String body;
    private String userName;
    private String name;
    private String imageUrl;
    private String messageTime;
    private boolean isStarred;
    private boolean isIncoming;


    public Message(String body, String userName, String name, String imageUrl,
                   String messageTime, boolean isStarred, boolean isIncoming){
        this.body = body;
        this.userName = userName;
        this.name = name;
        this.imageUrl = imageUrl;
        this.messageTime = messageTime;
        this.isStarred = isStarred;
        this.isIncoming = isIncoming;
    }


    public String getBody() {
        return body;
    }


    public String getUserName() {
        return userName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }


    public String getMessageTime() {
        return messageTime;
    }


    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public boolean isIncoming() {
        return isIncoming;
    }
}
