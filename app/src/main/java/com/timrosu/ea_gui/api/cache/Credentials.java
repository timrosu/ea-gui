package com.timrosu.ea_gui.api.cache;

public class Credentials { // "predpomnilnik" za avtentikacijsko kodo
    private static String bearer;

    public static String getBearer() {
        return bearer;
    }

    public static void setBearer(String bearer) {
        Credentials.bearer = bearer;
    }

}

