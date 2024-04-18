package com.timrosu.ea_gui.api.callback;

import java.util.Map;

public interface ChildCallback {
    void onFetchSuccess(Map<String,String> childMap);
    void onFetchError(Throwable throwable);
}
