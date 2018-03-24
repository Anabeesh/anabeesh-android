package com.rxmuhammadyoussef.anabeesh.store.model.article;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ArticleEntity extends RealmObject {

    @PrimaryKey
    private long id;
    private String heading;
    private String body;
    private long categoryId;
    private String userId;
    private String userName;
    private String userAvatarUrl;

    public ArticleEntity() {
        //Default constructor is required in order to realm to function
    }

    ArticleEntity(long id, String heading, String body, long categoryId, String userId, String userName, String userAvatarUrl) {
        this.id = id;
        this.heading = heading;
        this.body = body;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatarUrl = userAvatarUrl;
    }

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
