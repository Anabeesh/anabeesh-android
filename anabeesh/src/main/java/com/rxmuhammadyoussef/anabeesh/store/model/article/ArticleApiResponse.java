package com.rxmuhammadyoussef.anabeesh.store.model.article;

import com.google.gson.annotations.SerializedName;

public class ArticleApiResponse {

    @SerializedName("Id")
    private long id;

    @SerializedName("Heading")
    private String heading;

    @SerializedName("Body")
    private String body;

    @SerializedName("CategoryId")
    private long categoryId;

    @SerializedName("UserId")
    private String userId;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("ImageUrl")
    private String userAvatarUrl;

    long getId() {
        return id;
    }

    String getHeading() {
        return heading;
    }

    String getBody() {
        return body;
    }

    long getCategoryId() {
        return categoryId;
    }

    String getUserId() {
        return userId;
    }

    String getUserName() {
        return userName;
    }

    String getUserAvatarUrl() {
        return userAvatarUrl;
    }
}
