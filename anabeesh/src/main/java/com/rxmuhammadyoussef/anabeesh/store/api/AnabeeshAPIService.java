package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.store.model.user.UserApiResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AnabeeshAPIService {

    @POST("api/login")
    @FormUrlEncoded
    Call<UserApiResponse.DataResponse> login(
            @Header("Content-Type") String header,
            @Field("Email") String email,
            @Field("Password") String password);

    @POST("api/Register")
    @FormUrlEncoded
    Call<UserApiResponse.DataResponse> register(
            @Header("Content-Type") String header,
            @Field("Email") String email,
            @Field("Password") String password,
            @Field("FirstName") String firstName,
            @Field("LastName") String lastName);
}