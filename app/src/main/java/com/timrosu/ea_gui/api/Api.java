package com.timrosu.ea_gui.api;

import android.content.Context;

import com.timrosu.ea_gui.api.client.ApiClient;
import com.timrosu.ea_gui.api.model.response.items.AbsenceItem;
import com.timrosu.ea_gui.api.model.response.items.ExamItem;
import com.timrosu.ea_gui.api.model.response.items.GradeItem;
import com.timrosu.ea_gui.api.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Api {
    private List<ExamItem> examItemList;
    private List<GradeItem> gradeItemList;
    private List<AbsenceItem> absenceItemList;
    private final Map<String, String> childMap = new HashMap<>();

    private final Context context;
    private final ApiService apiService;

    public Api(Context context) {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
        this.context = context;
    }
/*
    // sprejme uporabnisko ime in geslo in ga shrani
    public boolean setLogin(String username, String password) {
        try {
            Call<LoginResponse> call = apiService.login(username, password);
            Response<LoginResponse> response = call.execute();
            LoginResponse loginResponse;

            if(response.isSuccessful()){
                loginResponse = response.body();
                if (loginResponse != null && Objects.equals(loginResponse.getStatus(), "ok")) {
                    if (!CryptoManager.checkCredentials(context)) {
                        CryptoManager.saveCredentials(context, username, password);
                    }
                    AuthData.setCookie(Objects.requireNonNull(response.headers().get("set-cookie"))); //predpomnenje piskotka
                    Log.i("status", loginResponse.getStatus());
                    Log.i("Cookie", AuthData.getCookie());
                    return true;
                }
            }

        } catch (IOException e) {
            Log.d(e.getLocalizedMessage(), Arrays.toString(e.getStackTrace()));
        }
        return false;
    }*/


/*
    public void setLogin(String username, String password, LoginCallback callback) {
        Call<LoginResponse> call = apiService.login(username, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    int code = response.code();
                    if (Objects.requireNonNull(loginResponse).getStatus().equals("ok")) {
                        if(!CryptoManager.checkCredentials(context)){
                            CryptoManager.saveCredentials(context, username, password);
                        }
                        AuthData.setCookie(Objects.requireNonNull(response.headers().get("set-cookie"))); //predpomnenje piskotka
                        Log.i("status", loginResponse.getStatus());
                        Log.i("Cookie", AuthData.getCookie());
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
*/

/*
    private void setCookie() { //metoda za pridobitev piskotka
        if (AuthData.getCookie() == null) {
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
    }

    private void setBearer() {
        if (AuthData.getBearer() == null) {
            setCookie();
            String cookie = AuthData.getCookie();
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
            Call<ResponseBody> call = apiService.getBearer(cookie);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        String html = null;
                        try {
                            assert responseBody != null;
                            html = responseBody.string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        String regex = "<meta name=\"access-token\" content=\"(.*?)\">"; //regex to extract from html document
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(html);

                        if (matcher.find()) {
                            AuthData.setBearer(matcher.group(1));
                            callback.onBearerReceived(AuthData.getBearer());
                        } else {
                            callback.onBearerError(new Exception("Access token not found in HTML"));
                        }
                    } else {
                        callback.onBearerError(new Exception("HTTP Error: " + response.code()));
                    }
                }


                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.e("BearerError", "Error getting bearer token", t);
                    callback.onBearerError(new Exception("Error getting bearer token", t));
                }

            });
        }
    }
*/

  /*  public Map<String, String> getChild(ChildCallback callback) {
        if (childMap.isEmpty()) {
//            setBearer();
            String bearer = AuthData.getBearer();

            Call<ChildResponse> call = apiService.getChild(bearer);
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
    }*/

    /*
    //TODO: error handling
    public List<ExamItem> getExams(ExamCallback callback) {
        if (examItemList == null || examItemList.isEmpty()) {
            setBearer();
            String bearer = AuthData.getBearer();

            Call<List<ExamResponse>> call = apiService.getExams(bearer);
            call.enqueue(new Callback<List<ExamResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<ExamResponse>> call, @NonNull Response<List<ExamResponse>> response) {
                    if (response.isSuccessful()) {
                        examItemList = response.body();
                        callback.onFetchSuccess(examItemList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<ExamResponse>> call, @NonNull Throwable throwable) {
                    callback.onFetchError(throwable);
                }
            });
        }
        return examItemList;
    }
    

    //TODO: error handling
    public List<GradeItem> getGrades(GradeCallback callback) {
        if (gradeItemList == null || gradeItemList.isEmpty()) {
//            setBearer();
            String bearer = AuthData.getBearer();

            Call<GradeResponse> call = apiService.getGrades(bearer);
            call.enqueue(new Callback<GradeResponse>() {
                @Override
                public void onResponse(@NonNull Call<GradeResponse> call, @NonNull Response<GradeResponse> response) {
                    if (response.isSuccessful()) {
                        GradeResponse gradeResponse = response.body();
                        assert gradeResponse != null;
                        gradeItemList = gradeResponse.getItems();
                        callback.onFetchSuccess(gradeItemList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GradeResponse> call, @NonNull Throwable t) {
                    Log.d("GradeError", Objects.requireNonNull(t.getLocalizedMessage()));
                    callback.onFetchError(t);
                }
            });
        }
        return gradeItemList;
    }



/*
    public List<GradeItem> getGrades(GradeCallback callback) {
        if (gradeItemList == null || gradeItemList.isEmpty()) {
            setBearer();
            String bearer = AuthData.getBearer();

            Call<GradeResponse> call = apiService.getGrades(bearer);
            call.enqueue(new Callback<GradeResponse>() {
                @Override
                public void onResponse(@NonNull Call<GradeResponse> call, @NonNull Response<GradeResponse> response) {
                    if (response.isSuccessful()) {
                        GradeResponse gradeResponse = response.body();
                        assert gradeResponse != null;
                        gradeItemList = gradeResponse.getItems();
                        callback.onFetchSuccess(gradeItemList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GradeResponse> call, @NonNull Throwable t) {
                    Log.d("GradeError", Objects.requireNonNull(t.getLocalizedMessage()));
                    callback.onFetchError(t);
                }
            });
        }
        return gradeItemList;
    }
*/

    /*
    //TODO: error handling
    public List<AbsenceItem> getAbsences(AbsenceCallback callback) {
        if (absenceItemList == null || absenceItemList.isEmpty()) {
            setBearer();
            String bearer = AuthData.getBearer();

            Call<List<AbsenceResponse>> call = apiService.getAbsences(bearer);
            call.enqueue(new Callback<List<AbsenceResponse>>() {
                @Override
                public void onResponse(@NonNull Call<List<AbsenceResponse>> call, @NonNull Response<List<AbsenceResponse>> response) {
                    if (response.isSuccessful()) {
                        absenceItemList = response.body();
                        callback.onFetchSuccess(absenceItemList);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<AbsenceResponse>> call, @NonNull Throwable t) {
                    callback.onFetchError(t);
                }
            });
        }
        return absenceItemList;
    }

     */
}
