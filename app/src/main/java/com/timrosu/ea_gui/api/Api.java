package com.timrosu.ea_gui.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.timrosu.ea_gui.client.ApiClient;
import com.timrosu.ea_gui.keystore.CryptoManager;
import com.timrosu.ea_gui.model.response.*;
import com.timrosu.ea_gui.service.ApiService;
import com.timrosu.ea_gui.api.cache.Credentials;

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
    public int setLogin(String username, String password) {
        final int[] code = new int[1];

        Call<LoginResponse> call = client.getApiService().login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    code[0] = response.code();

                    // preverjanje pravilnosti prijavnih podatkov
                    if (Objects.requireNonNull(loginResponse).getStatus().equals("ok")) {
                        cookie = response.headers().get("set-cookie"); //predpomnenje piskotka
                        assert cookie != null;
                        Log.i("Cookie",cookie);
                        Log.i("status",loginResponse.getStatus());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable throwable) {
                Log.d("LoginError", Objects.requireNonNull(throwable.getLocalizedMessage()));
            }
        });
        return code[0];
    }

    public void logout() { //odjava
        CryptoManager.deleteCredentials(context);
    }

    public void credentialCheck() { //povezava z razredom CryptoManager za preverjanje prisotnosti prijavnih podatkov
        CryptoManager.checkCredentials(context);
    }

    private String getCookie() { //metoda za pridobitev piskotka
        if (cookie == null) {
            setLogin(CryptoManager.getUsername(context), CryptoManager.getPassword(context));
        }
        return cookie;
    }

    private String getBearer() { //pridobi avtentikacijsko kodo
        if (Credentials.getBearer() == null) {
            Call<String> call = client.getApiService().getBearer(getCookie());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        String regex = "<meta name=\"access-token\" content=\"(.*?)\">"; //regex niz za izluscitev iz html dokumenta
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(Objects.requireNonNull(response.body()));

                        if (matcher.find()) {
                            Credentials.setBearer(matcher.group(1));
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                }
            });

        }
        return Credentials.getBearer();
    }

    public Map<String, String> getChild() {
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
                    }

                }

                @Override
                public void onFailure(Call<ChildResponse> call, Throwable throwable) {

                }

            });
        }

        return childMap;
    }

    //TODO: error handling
    public List<ExamResponse> getExams() {
        if (examResponseList.isEmpty()) {

            Call<List<ExamResponse>> call = client.getApiService().getExams(getBearer());
            call.enqueue(new Callback<List<ExamResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ExamResponse>> call, @NonNull Response<List<ExamResponse>> response) {
                    if (response.isSuccessful()) {
                        examResponseList = response.body();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ExamResponse>> call, @NonNull Throwable t) {
                    // Handle the failure
                }
            });
        }
        return examResponseList;
    }

    //TODO: error handling
    public List<GradeResponse> getGrades() {
//        if (gradeResponseList.isEmpty()) {

            Call<List<GradeResponse>> call = client.getApiService().getGrades(getBearer());
            call.enqueue(new Callback<List<GradeResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<GradeResponse>> call, @NonNull Response<List<GradeResponse>> response) {
                    if (response.isSuccessful()) {
                        gradeResponseList = response.body();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<GradeResponse>> call, @NonNull Throwable t) {
                    Log.d("GradeError", Objects.requireNonNull(t.getLocalizedMessage()));
                }
            });
//        }
        return gradeResponseList;
    }

    //TODO: error handling
    public List<AbsenceResponse> getAbsences() {
        if (absenceResponseList.isEmpty()) {

            Call<List<AbsenceResponse>> call = client.getApiService().getAbsences(getBearer());
            call.enqueue(new Callback<List<AbsenceResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<AbsenceResponse>> call, @NonNull Response<List<AbsenceResponse>> response) {
                    if (response.isSuccessful()) {
                        absenceResponseList = response.body();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<AbsenceResponse>> call, @NonNull Throwable t) {
                    // Handle the failure
                }
            });
        }
        return absenceResponseList;
    }


    //TODO: error handling
    public Map<String, String> getProfileInfo() {
        Call<ChildResponse> call = client.getApiService().getChild(getBearer());
        call.enqueue(new Callback<ChildResponse>() {
            @Override
            public void onResponse(@NonNull Call<ChildResponse> call, @NonNull Response<ChildResponse> response) {
                if (response.isSuccessful()) {

                    childMap.put("name", ChildResponse.getName());
                    childMap.put("age", ChildResponse.getAge());
                    childMap.put("gender", ChildResponse.getGender());
                    childMap.put("schoolyear", ChildResponse.getSchool_year());
                    childMap.put("id", ChildResponse.getId());
                    childMap.put("type", ChildResponse.getType());
                    childMap.put("plus_enabled", ChildResponse.getPlus_enabled());
                    childMap.put("date", ChildResponse.getDate());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ChildResponse> call, @NonNull Throwable throwable) {

            }
        });
        return childMap;
    }
}
