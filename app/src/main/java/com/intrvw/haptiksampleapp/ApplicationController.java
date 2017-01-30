package com.intrvw.haptiksampleapp;

import android.app.Application;
import android.content.Context;

import com.intrvw.haptiksampleapp.controller.DataController;
import com.intrvw.haptiksampleapp.controller.EventController;
import com.intrvw.haptiksampleapp.controller.MyRequester;

/**
 * Created by rainiksoni on 28/01/17.
 */

public class ApplicationController extends Application {

    Context context;

    public static ApplicationController controllerInstance = null;

    public ApplicationController(){
         controllerInstance = this;
    }

    public static ApplicationController getInstance(){
        return controllerInstance;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        DataController.getInstance().initRequestData();

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        EventController.getInstance().reset();
    }

    public MyRequester getVolleyRequester(){

        return MyRequester.getInstance(context);

    }
}
