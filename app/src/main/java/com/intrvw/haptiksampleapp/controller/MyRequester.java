package com.intrvw.haptiksampleapp.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by rainiksoni
 */

public class MyRequester {

    private static MyRequester _instance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mContext;


    /**
     * Returns instance of the class which is already created and maintains implementation of
     * single instance design pattern
     *
     * @param context current activity context
     * @return _instance of {@link MyRequester}
     */
    public static synchronized MyRequester getInstance(Context context){
        if (_instance == null){
            _instance = new MyRequester(context);
        }
        return _instance;
    }


    private MyRequester(Context context){
        mContext = context;
        mRequestQueue = getRequestQueue();

        mImageLoader  = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap>
                    cache = new LruCache<String, Bitmap>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {

            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
