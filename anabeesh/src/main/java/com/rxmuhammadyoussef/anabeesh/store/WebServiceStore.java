package com.rxmuhammadyoussef.anabeesh.store;

import android.util.Log;

import com.google.gson.Gson;
import com.rxmuhammadyoussef.anabeesh.events.error.NetworkConnectionError;
import com.rxmuhammadyoussef.anabeesh.events.error.WebServiceError;
import com.rxmuhammadyoussef.anabeesh.store.api.APIsUtil;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.LoginRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.RegisterRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.user.UserApiResponse;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

@ApplicationScope
class WebServiceStore {

    private static final String HEADER = "application/json";
    private static final int SUCCESS = 200;
    private static final int BAD_REQUEST = 400;
    private static final int SERVER_ERROR = 500;
    private final APIsUtil apisUtil;

    @Inject
    WebServiceStore(APIsUtil apisUtil) {
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

    private void processUserResponse(SingleEmitter<UserApiResponse.DataResponse> emitter, Response<UserApiResponse.DataResponse> response) {
        Log.d("MuhammadDebug", response.toString());
        switch (response.code()) {
            case SUCCESS:
                emitter.onSuccess(response.body());
                break;
            case BAD_REQUEST:
                emitter.onError(new WebServiceError(getAuthErrorMessage(response.errorBody())));
                break;
            case SERVER_ERROR:
                emitter.onError(new WebServiceError(response.message()));
                break;
            default:
                String message = response.message();
                if (message == null) {
                    message = "Unknown error message";
                }
                emitter.onError(new WebServiceError(message));
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
}
