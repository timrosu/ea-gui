package com.timrosu.ea_gui.api.callback;

import com.timrosu.ea_gui.model.response.ExamResponse;

import java.util.List;

public interface ExamCallback {
    void onFetchSuccess(List<ExamResponse> examResponseList);
    void onFetchError(Throwable throwable);
}
