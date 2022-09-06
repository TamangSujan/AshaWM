package com.ayata.urldatabase.static_methods;

public class Library {
    public static String splitAndGetFirst(String data, String splitSequence){
        String arrData[] = data.split(splitSequence);
        return arrData[0];
    }
}
