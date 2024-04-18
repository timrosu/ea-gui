package com.timrosu.ea_gui.service;

import com.timrosu.ea_gui.model.response.AbsenceResponse;
import com.timrosu.ea_gui.model.response.ChildResponse;
import com.timrosu.ea_gui.model.response.ExamResponse;
import com.timrosu.ea_gui.model.response.GradeResponse;
import com.timrosu.ea_gui.model.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "origin: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })
    @FormUrlEncoded
    @POST("/p/ajax_prijava")
    Call<LoginResponse> login(@Field("uporabnik") String username, @Field("geslo") String password);


    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "origin: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })
    @GET("/webapp")
    Call<String> getBearer(@Header("Cookie") String cookie);


    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "origin: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })
    @GET("/m/evaluations?filter=past")
    Call<List<GradeResponse>> getGrades(@Header("Authorization") String bearer);


    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "origin: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })
    @GET("/m/evaluations?filter=future")
    Call<List<ExamResponse>> getExams(@Header("Authorization") String bearer);


    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "origin: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })
    @GET("/m/absences")
    Call<List<AbsenceResponse>> getAbsences(@Header("Authorization") String bearer);


    @Headers({ // potrebni okvirji za komunikacijo z eA strežnikom
            "origin: https://www.easistent.com",
            "x-child-id: 370747",
            "x-client-platform: web",
            "x-client-version: 13",
            "useragent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.3", // lažni useragent niz
            "cookie: easistent_cookie=zapri"
    })
    @GET("/m/me/child")
    Call<ChildResponse> getChild(@Header("Authorization") String bearer);
}
