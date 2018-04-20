package com.rxmuhammadyoussef.anabeesh.store.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class QuestionRequestBody {

    @SerializedName("Headline")
    private final String headline;

    @SerializedName("Description")
    private final String body;

    @SerializedName("UserId")
    private final String userId;

    @SerializedName("CategoryId")
    private final String categoryId;

    public QuestionRequestBody(String headline, String body, String userId, String categoryId) {
        this.headline = headline;
        this.body = body;
        this.userId = userId;
        this.categoryId = categoryId;
    }
}
