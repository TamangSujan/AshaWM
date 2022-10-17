package com.ayata.urldatabase.static_files;

/**
 * This class contains two strings on string array.<br>
 * 0th position contains http code in string.<br>
 * 1th position contains http status in string.
 * Example:<br>
 * StatusCode.OK = {"200", "Ok"}
 */
public class StatusCode {
    public final static String[] OK = {"200", "Ok"};
    public final static String[] NO_CONTENT = {"204", "No Content"};
    public final static String[] BAD_REQUEST = {"400", "Bad Request"};
    public final static String[] INTERNAL_SERVER_ERROR = {"500", "Internal Server Error"};
}
