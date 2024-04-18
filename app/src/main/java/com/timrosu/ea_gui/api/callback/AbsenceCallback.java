package com.timrosu.ea_gui.api.callback;

import com.timrosu.ea_gui.model.response.AbsenceResponse;

import java.util.List;

public interface AbsenceCallback {
    void onFetchSuccess(List<AbsenceResponse> absenceResponseList);
    void onFetchError(Throwable throwable);
}
