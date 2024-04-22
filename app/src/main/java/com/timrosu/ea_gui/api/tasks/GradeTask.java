package com.timrosu.ea_gui.api.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.model.response.GradeResponse;
import com.timrosu.ea_gui.api.service.ApiService;
import com.timrosu.ea_gui.cache.AuthData;
import com.timrosu.ea_gui.cache.Data;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;

/** @noinspection deprecation*/
public class GradeTask extends AsyncTask<Void, Void, GradeResponse> {
    @Override
    protected GradeResponse doInBackground(Void... voids) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<GradeResponse> call = apiService.getGrades(AuthData.getBearer());
        try {
            Response<GradeResponse> response = call.execute();
            if (response.isSuccessful()) {
                GradeResponse gradeResponse = response.body();
                assert gradeResponse != null;
                Data.setGradeItems(gradeResponse.getItems());

                return gradeResponse;
            } else {
                Log.d("BearerTaskError","Request failed.");
            }
        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
