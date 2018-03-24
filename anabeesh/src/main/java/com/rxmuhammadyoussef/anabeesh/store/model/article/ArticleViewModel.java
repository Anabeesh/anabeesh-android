package com.rxmuhammadyoussef.anabeesh.store.model.article;

public class ArticleViewModel {

    public final long id;
    private final String heading;
    private final String body;
    private final long categoryId;
    private final String userId;
    private final String userName;
    private final String userAvatarUrl;

    ArticleViewModel(long id, String heading, String body, long categoryId, String userId, String userName, String userAvatarUrl) {
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

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof ArticleViewModel) {
            ArticleViewModel gameModel = (ArticleViewModel) obj;
            isEqual = gameModel.getId() == (this.id)
                    && gameModel.getHeading().contentEquals(this.heading)
                    && gameModel.getBody().contentEquals(this.body)
                    && gameModel.getCategoryId() == this.categoryId
                    && gameModel.getUserId().contentEquals(this.userId)
                    && gameModel.getUserName().contentEquals(this.userName)
                    && gameModel.getUserAvatarUrl().contentEquals(this.userAvatarUrl);
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return (int) id;
    }
}
