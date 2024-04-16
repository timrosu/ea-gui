package com.timrosu.ea_gui.api.responses;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    @SerializedName("errfields")
    private Errfields errfields;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public static class Errfields {
        @SerializedName("uporabnik")
        private String uporabnik;
    }
}
