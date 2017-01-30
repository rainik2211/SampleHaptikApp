package com.intrvw.haptiksampleapp.controller;

import android.os.Bundle;

/**
 * Created by rainiksoni on 26/12/16.
 */

public interface EventListener {

    /**
     * Callback method invoked to notify the activity of an event
     *
     * @param event
     * @param bundle
     * @return true if the event was handled
     */
    boolean handleEvent(int event, Bundle bundle);
}
