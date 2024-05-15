package com.timrosu.ea_gui.api.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.service.ApiService;
import com.timrosu.ea_gui.cache.AuthData;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/** @noinspection deprecation*/
public class BearerTask extends AsyncTask<Void, Void, ResponseBody> {
    @Override
    protected ResponseBody doInBackground(Void... voids) {
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<ResponseBody> call = apiService.getBearer(AuthData.getCookie());
        try {
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                String html = responseBody.string();

                String regex = "<meta name=\"access-token\" content=\"(.*?)\">"; //regex to extract from html document
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(html);

                if (matcher.find()) {
                    AuthData.setBearer(matcher.group(1));
                    Log.i("BearerTask","successful");
                } else {
                    Log.d("BearerTaskError","Access token not found in HTML");
                }
                return responseBody;
            } else {
                Log.d("BearerTaskError","Request failed.");
            }
        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
