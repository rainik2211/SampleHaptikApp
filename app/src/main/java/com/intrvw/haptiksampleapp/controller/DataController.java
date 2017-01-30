package com.intrvw.haptiksampleapp.controller;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.intrvw.haptiksampleapp.ApplicationController;
import com.intrvw.haptiksampleapp.BuildConfig;
import com.intrvw.haptiksampleapp.model.Message;
import com.intrvw.haptiksampleapp.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by rainiksoni on 28/01/17.
 */

public class DataController {

    public static final String TAG = DataController.class.getSimpleName();

    private static DataController _instance = new DataController();

    private DataController() {
        _instance = this;
    }

    public static final DataController getInstance() {
        return _instance;
    }

    public void initRequestData() {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "http://haptik.mobi/android/test_data/",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v(TAG, "response : " + response.toString());
                        try {
                            JSONArray array = response.getJSONArray("messages");
                            if (array.length() > 0) {
                                Log.v(TAG, "response array  size: " + array.length());
                                Log.v(TAG, "response array  : " + array.toString());

                                String body = null;
                                String userName = null;
                                String name = null;
                                String imageUrl = null;
                                String messageTime = null;
                                boolean isStarred = false;

                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject messageObject = (JSONObject) array.get(i);

                                    body = messageObject.getString("body");
                                    Log.v(TAG, "body message : "+body);

                                    userName = messageObject.getString("username");
                                    Log.v(TAG, "userName message : "+userName);

                                    name = messageObject.getString("Name");
                                    Log.v(TAG, "name message : "+name);

                                    imageUrl = messageObject.getString("image-url");
                                    Log.v(TAG, "imageUrl message : "+imageUrl);

                                    messageTime = messageObject.getString("message-time");
                                    Log.v(TAG, "messageTime message : "+messageTime);

                                    String key = String.valueOf(DataHolder.getInstance().
                                            getMessageDataMap().size() + 1);
                                    Log.v(TAG, "message : "+messageObject.toString()+" has key : "+key);

                                    Message message =  new Message(body, userName, name, imageUrl,
                                            messageTime, isStarred, true);

                                    addToMessageMap(message, key);

                                    addToUserMap(userName, name, imageUrl);

//                                    Bundle bundle = new Bundle();

                                    EventController.getInstance().notify(EventController.EVENT_MESSAGES_RECEIVED, null);

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            EventController.getInstance().notify(EventController.EVENT_BAD_REQUEST, null);
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventController.getInstance().notify(EventController.EVENT_REQUEST_TIMEOUT, null);
            }
        });

        ApplicationController.getInstance().getVolleyRequester().addToRequestQueue(req);

    }



    private void addToMessageMap(Message message, String key){
        //adding message object to MessageMap
        DataHolder.getInstance().getMessageDataMap().
                put(key,message);
    }


    private void addToUserMap(String key, String name, String avatarUrl){

        if (DataHolder.getInstance().getUserMessageMap().containsKey(key)){
            User user = DataHolder.getInstance().getUserMessageMap().get(key);
            user.setNumberOfMessages(user.getNumberOfMessages()+1);
        }else {
            User user = new User(name, avatarUrl);
            DataHolder.getInstance().getUserMessageMap().put(key, user);
            DataHolder.getInstance().getUserKeyReference().add(key);
            user.setNumberOfMessages(user.getNumberOfMessages()+1);
        }
    }

}
