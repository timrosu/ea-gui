package com.timrosu.ea_gui.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.timrosu.ea_gui.api.keystore.CryptoManager;
import com.timrosu.ea_gui.api.responses.*;
import com.timrosu.ea_gui.api.responses.wrappers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Api {
    Context context;
    private String cookie;
    private String bearer;
    private final Map<Integer, GradeItem> gradeMap = new HashMap<>();
    private final Map<Integer, ExamItem> examMap = new HashMap<>();
    private final Map<Integer, AbsenceItem> absenceMap = new HashMap<>();
    private final Map<String, String> childMap = new HashMap<>();

    private final ApiInterface apiInterface;

    public Api(Context context) {
        apiInterface = RetrofitClientInstance.getRetrofitInstance().create(ApiInterface.class);
        this.context = context;
    }

    // sprejme uporabnisko ime in geslo in ga shrani
    public String login(String username, String password) {
        final String[] message = {null};
        Call<LoginResponse> call = apiInterface.setLogin(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    // preverjanje pravilnosti prijavnih podatkov
                    if (Objects.requireNonNull(loginResponse).getStatus().equals("ok")) {
                        cookie = response.headers().get("set-cookie");
                        CryptoManager.saveCredentials(context, username, password); // shrani prijavne podatke na varno mesto
                    } else {
                        String loginResponseString = loginResponse.toString();
                        message[0] = loginResponseString.split("\\. ", -1)[0];
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable throwable) {
                message[0] = "Napaka";
            }

        });
        return message[0];
    }

    public void logout() {
        CryptoManager.deleteCredentials(context);
    }

    private String getBearer() {
        if (bearer == null) {
            Call<String> call = apiInterface.getBearer(cookie);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        // Handle the response body
                        String regex = "<meta name=\"access-token\" content=\"(.*?)\">";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(Objects.requireNonNull(response.body()));

                        if (matcher.find()) {
                            String bearer = matcher.group(1);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    // Handle failure
                }
            });

        }
        return bearer;
    }

    public String getWelcome() {
        if (bearer == null) {
            Call<String> call = apiInterface.getBearer(getBearer());
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        String regex = "<meta name=\"access-token\" content=\"(.*?)\">";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(Objects.requireNonNull(response.body()));

                        if (matcher.find()) {
                            String bearer = matcher.group(1);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    // Handle failure
                }
            });

        }
        return null;
    }

    //TODO: error handling
    public Map<Integer, ExamItem> getExams() {
        if (examMap.isEmpty()) {
            Call<ExamWrapper> call = apiInterface.getExams(getBearer());
            call.enqueue(new Callback<ExamWrapper>() {
                @Override
                public void onResponse(@NonNull Call<ExamWrapper> call, @NonNull Response<ExamWrapper> response) {
                    if (response.isSuccessful()) {
                        ExamWrapper examWrapper = response.body();
                        assert examWrapper != null;
                        int id = 0;
                        for (ExamItem examItem : examWrapper.getItems()) {
                            examMap.put(id, examItem);
                            id++;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExamWrapper> call, @NonNull Throwable t) {
                    // Handle the failure
                }
            });
        }
        return examMap;
    }

    //TODO: error handling
    public Map<Integer, GradeItem> getGrades() {
        if (examMap.isEmpty()) {
            Call<GradeWrapper> call = apiInterface.getGrades(getBearer());
            call.enqueue(new Callback<GradeWrapper>() {
                @Override
                public void onResponse(@NonNull Call<GradeWrapper> call, @NonNull Response<GradeWrapper> response) {
                    if (response.isSuccessful()) {
                        GradeWrapper gradeWrapper = response.body();
                        assert gradeWrapper != null;
                        int id = 0;
                        for (GradeItem gradeItem : gradeWrapper.getItems()) {
                            gradeMap.put(id, gradeItem);
                            id++;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GradeWrapper> call, @NonNull Throwable t) {
                    // Handle the failure
                }
            });
        }
        return gradeMap;
    }

    //TODO: error handling
    public Map<Integer, AbsenceItem> getAbsences() {
        if (absenceMap.isEmpty()) {
            Call<AbsenceWrapper> call = apiInterface.getAbsences(getBearer());
            call.enqueue(new Callback<AbsenceWrapper>() {
                @Override
                public void onResponse(@NonNull Call<AbsenceWrapper> call, @NonNull Response<AbsenceWrapper> response) {
                    if (response.isSuccessful()) {
                        AbsenceWrapper absenceWrapper = response.body();
                        assert absenceWrapper != null;
                        int id = 0;
                        for (AbsenceItem absenceItem : absenceWrapper.getItems()) {
                            absenceMap.put(id, absenceItem);
                            id++;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<AbsenceWrapper> call, @NonNull Throwable t) {
                    // Handle the failure
                }
            });
        }
        return absenceMap;
    }


    //TODO: error handling
    public Map<String, String> getProfileInfo() {
        Call<ChildResponse> call = apiInterface.getChild(getBearer());
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
