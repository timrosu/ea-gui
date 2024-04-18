package com.timrosu.ea_gui.api.callback;

public interface LoginCallback {
    void onLoginSuccess(int code);

    void onLoginFailure(Throwable throwable);
}
