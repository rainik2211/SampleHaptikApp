package com.intrvw.haptiksampleapp.controller;

import android.util.SparseArray;

import com.intrvw.haptiksampleapp.model.Message;
import com.intrvw.haptiksampleapp.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by rainiksoni on 28/01/17.
 */

public class DataHolder {

    private HashMap<String, Message> messageDataMap;

    private HashMap<String, User> userMessageMap;

    private ArrayList<String> userKeyReference;

    private DataHolder(){
        messageDataMap = new HashMap<>();
        userMessageMap = new HashMap<>();
        userKeyReference = new ArrayList<>();
    }

    private static DataHolder _instance = new DataHolder();

    public static final DataHolder getInstance(){
        return _instance;
    }

    public HashMap<String, Message> getMessageDataMap() {
        return messageDataMap;
    }

    public HashMap<String, User> getUserMessageMap() {
        return userMessageMap;
    }

    public ArrayList<String> getUserKeyReference() {
        return userKeyReference;
    }
}
