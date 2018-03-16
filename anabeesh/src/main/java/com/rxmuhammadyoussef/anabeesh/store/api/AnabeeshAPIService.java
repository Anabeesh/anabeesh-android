package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.LoginRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.RegisterRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AnabeeshAPIService {

    @POST("api/login")
    Call<UserApiResponse.DataResponse> login(
            @Header("content-type") String header,
            @Body LoginRequestBody loginRequestBody);

    @POST("api/Register")
    Call<UserApiResponse.DataResponse> register(
            @Header("content-type") String header,
            @Body RegisterRequestBody registerRequestBody);
}