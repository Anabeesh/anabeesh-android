package com.rxmuhammadyoussef.anabeesh.store.model.category;

public class CategoryModel {

    private final String id;
    private final String name;
    private final boolean following;

    CategoryModel(String id, String name, boolean following) {
        this.id = id;
        this.name = name;
        this.following = following;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFollowing() {
        return following;
    }
}
