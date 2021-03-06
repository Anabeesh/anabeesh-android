package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.QuestionRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.SearchRequestBody;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AnabeeshRxAPIService {

    @GET("api/articles/{userId}/{page}/{pageSize}")
    Single<List<ArticleApiResponse>> fetchArticles(
            @Path("userId") String userId,
            @Path("page") int pageNumber,
            @Path("pageSize") int pageSize);

    @GET("api/Categories/{userId}")
    Single<List<CategoryApiResponse>> fetchCategories(@Path("userId") String userId);

    @GET("api/homepage/toprated/{userId}")
    Single<List<QuestionApiResponse>> fetchTopRatedQuestions(@Path("userId") String userId);

    @GET("api/homepage/newest/{userId}")
    Single<List<QuestionApiResponse>> fetchNewestQuestions(@Path("userId") String userId);

    @POST("api/search/question")
    Observable<List<QuestionApiResponse>> searchQuestions(@Body SearchRequestBody requestBody);

    @GET("api/Follow/FollowCategory/{userId}/{categoryId}")
    Single<ResponseBody> followCategory(
            @Path("userId") String userId,
            @Path("categoryId") String categoryId);

    @GET("api/Follow/UnfollowCategory/{userId}/{categoryId}")
    Single<ResponseBody> unFollowCategory(
            @Path("userId") String userId,
            @Path("categoryId") String categoryId);

    @POST("api/questions")
    Single<ResponseBody> addQuestion(
            @Header("content-type") String header,
            @Body QuestionRequestBody requestBody);

    @GET("/api/answers/GetByQuestionId/{id}")
    Single<List<AnswerApiResponse>> fetchAnswers(@Path("id") String questionId);

    @GET("/api/questions/{id}")
    Single<QuestionApiResponse> fetchQuestion(@Path("id") String questionId);
}