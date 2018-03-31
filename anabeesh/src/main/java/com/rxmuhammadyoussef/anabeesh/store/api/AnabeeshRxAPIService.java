package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionApiResponse;

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

    @GET("api/Categories/{userId}")
    Single<List<CategoryApiResponse>> fetchCategories(
            @Path("userId") String userId);

    @GET("api/homepage/toprated/{userId}")
    Single<List<QuestionApiResponse>> fetchTopRatedQuestions(
            @Path("userId") String userId);

    @GET("api/homepage/newest/{userId}")
    Single<List<QuestionApiResponse>> fetchNewestQuestions(
            @Path("userId") String userId);
}