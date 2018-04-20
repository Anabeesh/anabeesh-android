package com.rxmuhammadyoussef.anabeesh.store;

import android.util.Log;

import com.google.gson.Gson;
import com.rxmuhammadyoussef.anabeesh.events.error.NetworkConnectionError;
import com.rxmuhammadyoussef.anabeesh.events.error.WebServiceError;
import com.rxmuhammadyoussef.anabeesh.store.api.APIsUtil;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionApiResponse;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.LoginRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.RegisterRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserApiResponse;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.util.PreferencesUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@ApplicationScope
class WebServiceStore {

    private static final String KEY_LAST_SAVED_PAGE_NUMBER = "lastSavedPageNumberKey";
    private static final String HEADER = "application/json";
    private static final int PAGE_SIZE = 10;

    private final PreferencesUtil preferencesUtil;
    private final APIsUtil apisUtil;

    @Inject
    WebServiceStore(PreferencesUtil preferencesUtil, APIsUtil apisUtil) {
        this.preferencesUtil = preferencesUtil;
        this.apisUtil = apisUtil;
    }

    Single<UserApiResponse.DataResponse> login(LoginRequestBody loginRequestBody) {
        return Single.create(emitter -> apisUtil.getAnabeeshAPIService()
                .login(HEADER, loginRequestBody)
                .enqueue(new Callback<UserApiResponse.DataResponse>() {
                    @Override
                    public void onResponse(Call<UserApiResponse.DataResponse> call, Response<UserApiResponse.DataResponse> response) {
                        processUserResponse(emitter, response);
                    }

                    @Override
                    public void onFailure(Call<UserApiResponse.DataResponse> call, Throwable t) {
                        emitter.onError(new NetworkConnectionError(t));
                    }
                }));
    }

    Single<UserApiResponse.DataResponse> register(RegisterRequestBody registerRequestBody) {
        return Single.create(emitter -> apisUtil.getAnabeeshAPIService()
                .register(HEADER, registerRequestBody)
                .enqueue(new Callback<UserApiResponse.DataResponse>() {
                    @Override
                    public void onResponse(Call<UserApiResponse.DataResponse> call, Response<UserApiResponse.DataResponse> response) {
                        processUserResponse(emitter, response);
                    }

                    @Override
                    public void onFailure(Call<UserApiResponse.DataResponse> call, Throwable t) {
                        emitter.onError(new NetworkConnectionError(t));
                    }
                }));
    }

    Single<List<ArticleApiResponse>> fetchArticles(String userId) {
        return apisUtil.getAnabeeshRxAPIService()
                .fetchArticles(userId, getPageNumber(), PAGE_SIZE)
                .doOnSuccess(ignored -> incrementPageNumberAndSave());
    }

    Single<List<CategoryApiResponse>> fetchCategories(String userId) {
        return apisUtil.getAnabeeshRxAPIService()
                .fetchCategories(userId);
    }

    Single<List<QuestionApiResponse>> fetchTopRatedQuestions(String userId) {
        return apisUtil.getAnabeeshRxAPIService()
                .fetchTopRatedQuestions(userId);
    }

    Single<List<QuestionApiResponse>> fetchNewestQuestions(String userId) {
        return apisUtil.getAnabeeshRxAPIService()
                .fetchNewestQuestions(userId);
    }

    Observable<List<QuestionApiResponse>> searchQuestions(String keyword) {
        return apisUtil.getAnabeeshRxAPIService()
                .searchQuestions(keyword);
    }

    Single<ResponseBody> followCategory(String userId, String categoryId) {
        return apisUtil.getAnabeeshRxAPIService()
                .followCategory(userId, categoryId);
    }

    Single<ResponseBody> unFollowCategory(String userId, String categoryId) {
        return apisUtil.getAnabeeshRxAPIService()
                .unFollowCategory(userId, categoryId);
    }

    private void processUserResponse(SingleEmitter<UserApiResponse.DataResponse> emitter, Response<UserApiResponse.DataResponse> response) {
        Log.d("MuhammadDebug", response.toString());
        switch (response.code()) {
            case 200:
                emitter.onSuccess(response.body());
                break;
            case 400:
                emitter.onError(new WebServiceError(getAuthErrorMessage(response.errorBody())));
                break;
            case 500:
                emitter.onError(new WebServiceError(response.message()));
                break;
            default:
                String message = response.message();
                if (message == null) {
                    message = "Unknown error message";
                }
                emitter.onError(new WebServiceError(message));
                break;
        }
    }

    private String getAuthErrorMessage(ResponseBody response) {
        Gson gson = new Gson();
        try {
            UserApiResponse.ErrorResponse errorResponse = gson.fromJson(response.string(), UserApiResponse.ErrorResponse.class);
            return errorResponse.getErrorMessage();
        } catch (Throwable throwable) {
            Timber.e(throwable);
        }
        return "Unknown bad request message";
    }

    private int getPageNumber() {
        return preferencesUtil.getInt(KEY_LAST_SAVED_PAGE_NUMBER) + 1;
    }

    private void incrementPageNumberAndSave() {
        int incrementedPageNumber = preferencesUtil.getInt(KEY_LAST_SAVED_PAGE_NUMBER) + 1;
        preferencesUtil.saveOrUpdateInt(KEY_LAST_SAVED_PAGE_NUMBER, incrementedPageNumber);
    }
}
