package com.rxmuhammadyoussef.anabeesh.store.model.article;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleViewModel implements Parcelable {

    public static final String TAG = ArticleViewModel.class.getSimpleName();
    public final long id;
    private final String heading;
    private final String body;
    private final long categoryId;
    private final String userId;
    private final String userName;
    private final String userAvatarUrl;
    private final String coverUrl;

    ArticleViewModel(long id, String heading, String body, long categoryId, String userId, String userName, String userAvatarUrl, String coverUrl) {
        this.id = id;
        this.heading = heading;
        this.body = body;
        this.categoryId = categoryId;
        this.userId = userId;
        this.userName = userName;
        this.userAvatarUrl = userAvatarUrl;
        this.coverUrl = coverUrl;
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

    public String getCoverUrl() {
        return coverUrl;
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

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.heading);
        dest.writeString(this.body);
        dest.writeLong(this.categoryId);
        dest.writeString(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userAvatarUrl);
        dest.writeString(this.coverUrl);
    }

    protected ArticleViewModel(Parcel in) {
        this.id = in.readLong();
        this.heading = in.readString();
        this.body = in.readString();
        this.categoryId = in.readLong();
        this.userId = in.readString();
        this.userName = in.readString();
        this.userAvatarUrl = in.readString();
        this.coverUrl = in.readString();
    }

    public static final Parcelable.Creator<ArticleViewModel> CREATOR = new Parcelable.Creator<ArticleViewModel>() {
        @Override
        public ArticleViewModel createFromParcel(Parcel source) {return new ArticleViewModel(source);}

        @Override
        public ArticleViewModel[] newArray(int size) {return new ArticleViewModel[size];}
    };
}
