package com.rxmuhammadyoussef.anabeesh.store.model.answer;

import com.google.gson.annotations.SerializedName;

public class AnswerApiResponse {

    @SerializedName("Id")
    private String id;

    @SerializedName("Body")
    private String body;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("QuestionId")
    private String questionId;

    String getId() {
        return id;
    }

    String getBody() {
        return body;
    }

    String getUserId() {
        return userId;
    }

    String getQuestionId() {
        return questionId;
    }
}