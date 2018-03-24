package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleApiResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnabeeshRxAPIService {

    @GET("api/articles/{userId}/{page}/{pageSize}")
    Single<List<ArticleApiResponse>> fetchArticles(
            @Path("userId") String userId,
            @Path("page") int pageNumber,
            @Path("pageSize") int pageSize);
}