package com.rxmuhammadyoussef.anabeesh.store.model.article;

public class ArticleModel {

    public final long id;
    private final String heading;
    private final String body;
    private final long categoryId;
    private final String userId;
    private final String userName;
    private final String userAvatarUrl;

    ArticleModel(long id, String heading, String body, long categoryId, String userId, String userName, String userAvatarUrl) {
        this.id = id;
        this.heading = heading;
        this.body = body;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatarUrl = userAvatarUrl;
    }

    public long getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getBody() {
        return body;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }
}
