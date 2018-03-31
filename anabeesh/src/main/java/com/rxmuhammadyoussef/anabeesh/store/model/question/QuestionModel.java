package com.rxmuhammadyoussef.anabeesh.store.model.question;

public class QuestionModel {

    private final String id;
    private final String userId;
    private final String headline;
    private final String description;
    private final String categoryId;
    private final String numberOfAnswers;
    private final String upVotes;
    private final String downVotes;
    private final boolean topRated;
    private final boolean newFeed;

    QuestionModel(String id, String userId, String headline, String description, String categoryId, String numberOfAnswers, String upVotes,
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

    public String getUserId() {
        return userId;
    }

    public String getHeadline() {
        return headline;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getNumberOfAnswers() {
        return numberOfAnswers;
    }

    public String getUpVotes() {
        return upVotes;
    }

    public String getDownVotes() {
        return downVotes;
    }

    public boolean isTopRated() {
        return topRated;
    }

    public boolean isNewFeed() {
        return newFeed;
    }
}
