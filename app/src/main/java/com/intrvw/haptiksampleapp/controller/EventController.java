package com.intrvw.haptiksampleapp.controller;

import android.os.Bundle;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by rainiksoni on 26/12/16.
 *
 * single instance implementation of EventController to implement the http requests
 */

public class EventController {

    // Array of listeners of all the events
    private CopyOnWriteArrayList<EventListener> _listeners;

    public static final int EVENT_MESSAGES_RECEIVED = 0;

    public static final int EVENT_STAR_MESSAGE_ADDED = 1;

    public static final int EVENT_BAD_REQUEST = 2;

    public static final int EVENT_REQUEST_TIMEOUT = 3;

    public static final int EVENT_REQUEST_EDUCATION_ADD = 4;




    private EventController(){
        init();
    }


    /**
     * holder to allow lazy initialization of singleton thread safe
     */
    private static class EventControllerHolder{
        public static EventController instance = new EventController();
    }

    public static EventController getInstance()
    {
        return EventControllerHolder.instance;
    }

    /**
     * Every UI element Activity or Fragment has to register to get notified
     * @param listener EventListener object that will be notified in handleEvent method
     */
    public void register(EventListener listener){
        synchronized (_listeners){

            if (listener != null && !_listeners.contains(listener)){
                _listeners.add(listener);
            }
        }
    }


    public void unRegister(EventListener listener){
        if (_listeners != null && _listeners.contains(listener)){

            _listeners.remove(listener);
        }
    }

    public boolean notify(int event, Bundle bundle){

        boolean eventHandled = false;

        for (EventListener listener : _listeners)
        {
            // Notify the EventListeners to handle the particular event
            eventHandled |= listener.handleEvent(event, bundle);
        }
        return eventHandled;

    }

    public void reset(){
        //Reinitialize while closing the application
        init();
    }


    private void init(){
        _listeners = new CopyOnWriteArrayList<EventListener>();
    }








}
