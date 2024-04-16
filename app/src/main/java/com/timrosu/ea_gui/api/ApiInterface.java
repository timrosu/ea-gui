package com.timrosu.ea_gui.api;

import com.timrosu.ea_gui.api.responses.ChildResponse;
import com.timrosu.ea_gui.api.responses.LoginResponse;
import com.timrosu.ea_gui.api.responses.wrappers.AbsenceWrapper;
import com.timrosu.ea_gui.api.responses.wrappers.ExamWrapper;
import com.timrosu.ea_gui.api.responses.wrappers.GradeWrapper;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "host: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "x-app-name: child",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })

    @FormUrlEncoded
    @POST("/p/ajax_prijava")
    Call<LoginResponse> setLogin(@Field("username") String username, @Field("password") String password);

    @GET("/webapp")
    Call<String> getBearer(@Header("Cookie") String cookie);

    @GET("/m/evaluations?filter=past")
    Call<GradeWrapper> getGrades(@Header("Authorization") String bearer);

    @GET("/m/evaluations?filter=future")
    Call<ExamWrapper> getExams(@Header("Authorization") String bearer);

    @GET("/m/absences")
    Call<AbsenceWrapper> getAbsences(@Header("Authorization") String bearer);

    @GET("/m/me/child")
    Call<ChildResponse> getChild(@Header("Authorization") String bearer);
}
