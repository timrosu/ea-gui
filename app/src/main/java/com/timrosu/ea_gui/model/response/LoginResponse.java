package com.timrosu.ea_gui.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;
    public String getStatus() {
        return status;
    }

    public String getRemainingAttempts() { //preostale neuspesne prijave
        return message;
    }

}
