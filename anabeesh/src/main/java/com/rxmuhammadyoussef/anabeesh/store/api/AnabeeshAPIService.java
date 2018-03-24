package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.LoginRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.RegisterRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnabeeshAPIService {

    @POST("api/login")
    Call<UserApiResponse.DataResponse> login(
            @Header("content-type") String header,
            @Body LoginRequestBody loginRequestBody);

    @POST("api/Register")
    Call<UserApiResponse.DataResponse> register(
            @Header("content-type") String header,
            @Body RegisterRequestBody registerRequestBody);

    @GET("api/articles/{userId}/{page}/{pageSize}")
    Call<List<ArticleApiResponse>> fetchArticles(
            @Path("userId") String userId,
            @Path("page") int pageNumber,
            @Path("pageSize") int pageSize);
}