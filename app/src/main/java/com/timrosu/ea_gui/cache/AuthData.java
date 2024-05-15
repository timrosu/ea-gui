package com.timrosu.ea_gui.cache;

import androidx.annotation.NonNull;

public class AuthData { // "predpomnilnik" za avtentikacijsko kodo
    private static String cookie;
    public static String getCookie() {
        return cookie;
    }
    public static void setCookie(@NonNull String cookie) {
        AuthData.cookie = cookie.split(";",-1)[0];
    }

    private static String status="running"; // possible values: running, success, fail
    public static String getStatus() {
        return status;
    }
    public static void setStatus(String status) {
        AuthData.status = status;
    }

    private static String message;
    public static String getMessage() {
        return message;
    }
    public static void setMessage(String message) {
        AuthData.message = message;
    }

    private static String bearer;
    public static String getBearer() {
        return bearer;
    }
    public static void setBearer(String bearer) {
        AuthData.bearer = bearer;
    }

}

