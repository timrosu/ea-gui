package com.timrosu.ea_gui.api.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.model.response.ExamResponse;
import com.timrosu.ea_gui.api.service.ApiService;
import com.timrosu.ea_gui.cache.AuthData;
import com.timrosu.ea_gui.cache.Data;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;

/** @noinspection deprecation*/
public class ExamTask extends AsyncTask<Void, Void, ExamResponse> {
    @Override
    protected ExamResponse doInBackground(Void... voids) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<ExamResponse> call = apiService.getExams(AuthData.getBearer());
        try {
            Response<ExamResponse> response = call.execute();
            if (response.isSuccessful()) {
                ExamResponse examResponse = response.body();
                assert examResponse != null;
                Data.setExamItems(examResponse.getItems());

                return examResponse;
            } else {
                Log.d("BearerTaskError","Request failed.");
            }
        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
