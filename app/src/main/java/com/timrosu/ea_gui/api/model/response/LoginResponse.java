package com.timrosu.ea_gui.api.model.response;

public class LoginResponse {
    private String status;
    private String message;
    public String getStatus() {
        return this.status;
    }

    public String getMessage() { //preostale neuspesne prijave
        return message;
    }

}
