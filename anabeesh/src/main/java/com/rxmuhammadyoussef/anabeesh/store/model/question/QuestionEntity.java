package com.rxmuhammadyoussef.anabeesh.store.model.question;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class QuestionEntity extends RealmObject {

    @PrimaryKey
    private String id;
    private String userId;
    private String headline;
    private String description;
    private String categoryId;
    private String numberOfAnswers;
    private String upVotes;
    private String downVotes;
    private boolean topRated;
    private boolean newFeed;

    public QuestionEntity() {
        // public default constructor is required
    }

    QuestionEntity(String id, String userId, String headline, String description, String categoryId, String numberOfAnswers, String upVotes,
                   String downVotes, boolean topRated, boolean newFeed) {
        this.id = id;
        this.userId = userId;
        this.headline = headline;
        this.description = description;
        this.categoryId = categoryId;
        this.numberOfAnswers = numberOfAnswers;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.topRated = topRated;
        this.newFeed = newFeed;
    }

    public String getId() {
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

    boolean isTopRated() {
        return topRated;
    }

    boolean isNewFeed() {
        return newFeed;
    }
}
