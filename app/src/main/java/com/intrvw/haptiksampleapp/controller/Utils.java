package com.intrvw.haptiksampleapp.controller;

/**
 * Created by rainiksoni on 29/01/17.
 */

public class Utils {

    public static String getTime(String time){
        String s = null;
        int index1 = time.lastIndexOf("T")+1;
        int index2 = time.lastIndexOf(":");

        s = time.substring(index1, index2);

        return s;
    }
}
