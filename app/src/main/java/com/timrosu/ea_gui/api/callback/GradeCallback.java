package com.timrosu.ea_gui.api.callback;

import com.timrosu.ea_gui.model.response.GradeResponse;

import java.util.List;

public interface GradeCallback {
    void onFetchSuccess(List<GradeResponse> gradeResponseList);
    void onFetchError(Throwable throwable);
}
