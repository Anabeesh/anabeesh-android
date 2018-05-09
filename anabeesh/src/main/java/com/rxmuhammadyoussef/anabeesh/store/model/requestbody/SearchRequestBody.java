package com.rxmuhammadyoussef.anabeesh.store.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class SearchRequestBody {

    @SerializedName("Question")
    private final String keyword;

    public SearchRequestBody(String keyword) {this.keyword = keyword;}
}
