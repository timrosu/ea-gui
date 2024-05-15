package com.timrosu.ea_gui.api.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.timrosu.ea_gui.cache.AuthData;
import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.keystore.CryptoManager;
import com.timrosu.ea_gui.api.model.request.LoginRequest;
import com.timrosu.ea_gui.api.model.response.LoginResponse;
import com.timrosu.ea_gui.api.service.ApiService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class LoginTask extends AsyncTask<Void, Void, LoginResponse> {
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected LoginResponse doInBackground(Void... voids) {
        AuthData.setStatus("running");
        ApiService apiService = ApiClient.getRetrofit().create(ApiService.class);
        Call<LoginResponse> call;
        if(CryptoManager.checkCredentials(context)){
            call = apiService.login(CryptoManager.getUsername(context), CryptoManager.getPassword(context));
        } else {
            call = apiService.login(LoginRequest.getUporabnik(), LoginRequest.getGeslo());
        }
        try {
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                LoginResponse loginResponse = response.body();
                if (Objects.equals(Objects.requireNonNull(loginResponse).getStatus(), "ok")) {
                    if (!CryptoManager.checkCredentials(context)) {
                        CryptoManager.saveCredentials(context, LoginRequest.getUporabnik(), LoginRequest.getGeslo());
                    }
                    AuthData.setCookie(Objects.requireNonNull(response.headers().get("set-cookie"))); //predpomnenje piskotka
                    Log.d("Cookie", AuthData.getCookie());
                    AuthData.setStatus("success");
                } else {
                    AuthData.setStatus("fail");
                    AuthData.setMessage(loginResponse.getMessage());
                }
                return loginResponse;
            } else {
                AuthData.setStatus("fail");
            }
        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
