package com.rxmuhammadyoussef.anabeesh.store.model.category;

import com.google.gson.annotations.SerializedName;

public class CategoryApiResponse {

    @SerializedName("Id")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("IsFollowing")
    private boolean following;

    String getId() {
        return id;
    }

    String getName() {
        return name;
    }

    boolean isFollowing() {
        return following;
    }
}
