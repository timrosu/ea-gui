package com.timrosu.ea_gui.api.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.model.response.AbsenceResponse;
import com.timrosu.ea_gui.api.service.ApiService;
import com.timrosu.ea_gui.cache.AuthData;
import com.timrosu.ea_gui.cache.Data;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;

/** @noinspection deprecation*/
public class AbsenceTask extends AsyncTask<Void, Void, AbsenceResponse> {
    @Override
    protected AbsenceResponse doInBackground(Void... voids) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<AbsenceResponse> call = apiService.getAbsences(AuthData.getBearer());
        try {
            Response<AbsenceResponse> response = call.execute();
            if (response.isSuccessful()) {
                AbsenceResponse absenceResponse = response.body();
                assert absenceResponse != null;
                Data.setAbsenceItems(absenceResponse.getItems());

                return absenceResponse;
            } else {
                Log.d("BearerTaskError","Request failed.");
            }
        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
