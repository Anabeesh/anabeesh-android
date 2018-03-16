package com.rxmuhammadyoussef.anabeesh.store.api;

import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@ApplicationScope
public class APIsUtil {

    private static final String BASE_URL = "http://anabeesh1-001-site1.atempurl.com/";

    private static Retrofit anabeeshRetrofit;

    @Inject
    APIsUtil() {
        //No extra logic needed
    }

    public AnabeeshAPIService getAnabeeshAPIService() {
        return getAnabeeshClient().create(AnabeeshAPIService.class);
    }

    private Retrofit getAnabeeshClient() {
        if (anabeeshRetrofit == null) {
            anabeeshRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return anabeeshRetrofit;
    }
}
