package com.rxmuhammadyoussef.anabeesh.store.model.question;

import com.google.gson.annotations.SerializedName;

public class QuestionApiResponse {

    @SerializedName("Id")
    private String id;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("Headline")
    private String headline;

    @SerializedName("Description")
    private String description;

    @SerializedName("CategoryId")
    private String categoryId;

    @SerializedName("NumberOfAnswers")
    private String numberOfAnswers;

    @SerializedName("Upvotes")
    private String upVotes;

    @SerializedName("Downvotes")
    private String downVotes;

    String getId() {
        return id;
    }

    String getUserId() {
        return userId;
    }

    String getHeadline() {
        return headline;
    }

    String getDescription() {
        return description;
    }

    String getCategoryId() {
        return categoryId;
    }

    String getNumberOfAnswers() {
        return numberOfAnswers;
    }

    String getUpVotes() {
        return upVotes;
    }

    String getDownVotes() {
        return downVotes;
    }
}
