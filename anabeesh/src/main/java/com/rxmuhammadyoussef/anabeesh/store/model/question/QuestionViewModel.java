package com.rxmuhammadyoussef.anabeesh.store.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionViewModel implements Parcelable {

    private final String id;
    private final String userId;
    private final String headline;
    private final String description;
    private final String categoryId;
    private final String numberOfAnswers;
    private final String upVotes;
    private final String downVotes;
    private final String coverUrl;
    private final boolean topRated;
    private final boolean newFeed;

    QuestionViewModel(String id, String userId, String headline, String description, String categoryId, String numberOfAnswers, String upVotes,
                      String downVotes, String coverUrl, boolean topRated, boolean newFeed) {
        this.id = id;
        this.userId = userId;
        this.headline = headline;
        this.description = description;
        this.categoryId = categoryId;
        this.numberOfAnswers = numberOfAnswers;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
        this.coverUrl = coverUrl;
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

    public String getCoverUrl() {
        return coverUrl;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof QuestionViewModel) {
            QuestionViewModel gameModel = (QuestionViewModel) obj;
            isEqual = gameModel.getId().contentEquals(this.id)
                    && gameModel.getHeadline().contentEquals(this.headline)
                    && gameModel.getDescription().contentEquals(this.description)
                    && gameModel.getCategoryId().contentEquals(this.categoryId)
                    && gameModel.getUserId().contentEquals(this.userId)
                    && gameModel.getUpVotes().contentEquals(this.upVotes)
                    && gameModel.getDownVotes().contentEquals(this.downVotes)
                    && gameModel.getNumberOfAnswers().contentEquals(this.numberOfAnswers);
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return id.length() + userId.length() + numberOfAnswers.length();
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.headline);
        dest.writeString(this.description);
        dest.writeString(this.categoryId);
        dest.writeString(this.numberOfAnswers);
        dest.writeString(this.upVotes);
        dest.writeString(this.downVotes);
        dest.writeString(this.coverUrl);
        dest.writeByte(this.topRated ? (byte) 1 : (byte) 0);
        dest.writeByte(this.newFeed ? (byte) 1 : (byte) 0);
    }

    protected QuestionViewModel(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.headline = in.readString();
        this.description = in.readString();
        this.categoryId = in.readString();
        this.numberOfAnswers = in.readString();
        this.upVotes = in.readString();
        this.downVotes = in.readString();
        this.coverUrl = in.readString();
        this.topRated = in.readByte() != 0;
        this.newFeed = in.readByte() != 0;
    }

    public static final Parcelable.Creator<QuestionViewModel> CREATOR = new Parcelable.Creator<QuestionViewModel>() {
        @Override
        public QuestionViewModel createFromParcel(Parcel source) {return new QuestionViewModel(source);}

        @Override
        public QuestionViewModel[] newArray(int size) {return new QuestionViewModel[size];}
    };
}
