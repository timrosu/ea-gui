package com.timrosu.ea_gui.model.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("status")
    private String status;


    public String getStatus() {
        return status;
    }

}
