package com.timrosu.ea_gui.api.model.request;

public class LoginRequest {
    static String uporabnik;

    public static String getUporabnik() {
        return uporabnik;
    }

    public static void setUporabnik(String uporabnik) {
        LoginRequest.uporabnik = uporabnik;
    }

    private static String geslo;

    public static String getGeslo() {
        return geslo;
    }

    public static void setGeslo(String geslo) {
        LoginRequest.geslo = geslo;
    }

}
