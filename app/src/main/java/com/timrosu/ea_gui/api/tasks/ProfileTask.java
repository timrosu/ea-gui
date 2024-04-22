package com.timrosu.ea_gui.api.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.model.response.ChildResponse;
import com.timrosu.ea_gui.api.service.ApiService;
import com.timrosu.ea_gui.cache.AuthData;
import com.timrosu.ea_gui.cache.Data;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @noinspection deprecation
 */
public class ProfileTask extends AsyncTask<Void, Void, ChildResponse> {

    @Override
    protected ChildResponse doInBackground(Void... voids) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<ChildResponse> call = apiService.getChild(AuthData.getBearer());
        try {
            Response<ChildResponse> response = call.execute();
            if (response.isSuccessful()) {
                ChildResponse childResponse = response.body();
                assert childResponse != null;
                Data.childMap.put("name", childResponse.getName());
                Data.childMap.put("age", childResponse.getAge());
                Data.childMap.put("gender", childResponse.getGender());
                Data.childMap.put("school_year", childResponse.getSchool_year());
                Data.childMap.put("id", childResponse.getId());
                Data.childMap.put("type", childResponse.getType());
                Data.childMap.put("plus", childResponse.getPlus_enabled());

                return childResponse;
            } else {
                Log.d("ChildTaskError", "Request failed.");
            }
        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
