package com.timrosu.ea_gui.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.timrosu.ea_gui.api.cache.Credentials;
import com.timrosu.ea_gui.api.callback.AbsenceCallback;
import com.timrosu.ea_gui.api.callback.ChildCallback;
import com.timrosu.ea_gui.api.callback.ExamCallback;
import com.timrosu.ea_gui.api.callback.GradeCallback;
import com.timrosu.ea_gui.api.callback.LoginCallback;
import com.timrosu.ea_gui.client.ApiClient;
import com.timrosu.ea_gui.keystore.CryptoManager;
import com.timrosu.ea_gui.model.response.AbsenceResponse;
import com.timrosu.ea_gui.model.response.ChildResponse;
import com.timrosu.ea_gui.model.response.ExamResponse;
import com.timrosu.ea_gui.model.response.GradeResponse;
import com.timrosu.ea_gui.model.response.LoginResponse;
import com.timrosu.ea_gui.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {
    private String cookie; //predpomnenje pistoktka
    private final Map<String, String> childMap = new HashMap<>();
    List<ExamResponse> examResponseList;
    List<GradeResponse> gradeResponseList;
    List<AbsenceResponse> absenceResponseList;
    private final Context context;
    ApiClient client = new ApiClient();
    private final ApiService apiService;

    public Api(Context context) {
        this.context = context;
        apiService = client.getRetrofit().create(ApiService.class);
    }

    // sprejme uporabnisko ime in geslo in ga shrani
    public void setLogin(String username, String password, LoginCallback callback) {
        Call<LoginResponse> call = client.getApiService().login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    int code = response.code();
                    if (Objects.requireNonNull(loginResponse).getStatus().equals("ok")) {
                        CryptoManager.saveCredentials(context, username, password);
                        cookie = response.headers().get("set-cookie"); //predpomnenje piskotka
                        assert cookie != null;
                        Log.i("Cookie", cookie);
                        Log.i("status", loginResponse.getStatus());
                        callback.onLoginSuccess(code);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable throwable) {
                callback.onLoginFailure(throwable);
            }
        });
    }

    public void logout() { //odjava
        CryptoManager.deleteCredentials(context);
    }

    public boolean credentialCheck() { //povezava z razredom CryptoManager za preverjanje prisotnosti prijavnih podatkov
        return CryptoManager.checkCredentials(context);
    }

    private String getCookie() { //metoda za pridobitev piskotka
        if (cookie == null) {
            setLogin(CryptoManager.getUsername(context), CryptoManager.getPassword(context), new LoginCallback() {
                @Override
                public void onLoginSuccess(int code) {
                    // Handle successful login
                    Log.i("getCookieSuccess", "getCookie successful with code: " + code);
                }

                @Override
                public void onLoginFailure(Throwable throwable) {
                    // Handle login failure
                    Log.e("getCookieError", "Login failed", throwable);
                }
            });
        }
        return cookie;
    }

    private String getBearer() {
        class BearerCallback {
            void onBearerReceived(String bearer) {
                // Handle the received bearer token
                Log.i("BearerToken", "Received bearer token: " + bearer);
            }

            void onBearerError(Throwable throwable) {
                // Handle the error
                Log.e("BearerError", "Error getting bearer token", throwable);
            }
        }
        // Instantiate the callback
        BearerCallback callback = new BearerCallback();
        if (Credentials.getBearer() == null) {
            Call<String> call = client.getApiService().getBearer(getCookie());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        String html = response.body();
                        assert html != null;
                        Log.i("BearerTokenBody", html);

                        String regex = "<meta name=\"access-token\" content=\"(.*?)\">"; //regex to extract from html document
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(html);

                        if (matcher.find()) {
                            Credentials.setBearer(matcher.group(1));
                            callback.onBearerReceived(Credentials.getBearer());
                        } else {
                            callback.onBearerError(new Exception("Access token not found in HTML"));
                        }
                    } else {
                        callback.onBearerError(new Exception("HTTP Error: " + response.code()));
                    }
                }


                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Log.e("BearerError", "Error getting bearer token", t);
                    callback.onBearerError(new Exception("Error getting bearer token", t));
                }

            });
        } else {
            callback.onBearerReceived(Credentials.getBearer());
        }
        return Credentials.getBearer();
    }

    public Map<String, String> getChild(ChildCallback callback) {
        if (childMap.isEmpty()) {

            Call<ChildResponse> call = client.getApiService().getChild(getBearer());
            call.enqueue(new Callback<ChildResponse>() {
                @Override
                public void onResponse(@NonNull Call<ChildResponse> call, @NonNull Response<ChildResponse> response) {
                    if (response.isSuccessful()) {
                        childMap.put("name", ChildResponse.getName());
                        childMap.put("age", ChildResponse.getAge());
                        childMap.put("gender", ChildResponse.getGender());
                        childMap.put("school_year", ChildResponse.getSchool_year());
                        childMap.put("id", ChildResponse.getId());
                        childMap.put("type", ChildResponse.getType());
                        childMap.put("plus", ChildResponse.getPlus_enabled());
                        childMap.put("date", ChildResponse.getDate());

                        callback.onFetchSuccess(childMap);
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ChildResponse> call, @NonNull Throwable throwable) {
                    callback.onFetchError(throwable);
                }

            });
        }

        return childMap;
    }

    //TODO: error handling
    public List<ExamResponse> getExams(ExamCallback callback) {
//        if (examResponseList.isEmpty()) {

        Call<List<ExamResponse>> call = client.getApiService().getExams(getBearer());
        call.enqueue(new Callback<List<ExamResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<ExamResponse>> call, @NonNull Response<List<ExamResponse>> response) {
                if (response.isSuccessful()) {
                    examResponseList = response.body();
                    callback.onFetchSuccess(examResponseList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ExamResponse>> call, @NonNull Throwable throwable) {
                callback.onFetchError(throwable);
            }
        });
//        }
        return examResponseList;
    }

    //TODO: error handling
    public List<GradeResponse> getGrades(GradeCallback callback) {
//        if (gradeResponseList.isEmpty()) {

        Call<List<GradeResponse>> call = client.getApiService().getGrades(getBearer());
        call.enqueue(new Callback<List<GradeResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<GradeResponse>> call, @NonNull Response<List<GradeResponse>> response) {
                if (response.isSuccessful()) {
                    gradeResponseList = response.body();
                    callback.onFetchSuccess(gradeResponseList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GradeResponse>> call, @NonNull Throwable t) {
                Log.d("GradeError", Objects.requireNonNull(t.getLocalizedMessage()));
                callback.onFetchError(t);
            }
        });
//        }
        return gradeResponseList;
    }

    //TODO: error handling
    public List<AbsenceResponse> getAbsences(AbsenceCallback callback) {
//        if (absenceResponseList.isEmpty()) {

        Call<List<AbsenceResponse>> call = client.getApiService().getAbsences(getBearer());
        call.enqueue(new Callback<List<AbsenceResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<AbsenceResponse>> call, @NonNull Response<List<AbsenceResponse>> response) {
                if (response.isSuccessful()) {
                    absenceResponseList = response.body();
                    callback.onFetchSuccess(absenceResponseList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<AbsenceResponse>> call, @NonNull Throwable t) {
                callback.onFetchError(t);
            }
        });
//        }
        return absenceResponseList;
    }

}
