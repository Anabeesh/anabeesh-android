package com.rxmuhammadyoussef.anabeesh.store.model.category;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoryEntity extends RealmObject {

    @PrimaryKey
    private String id;
    private String name;
    private boolean following;

    public CategoryEntity() {
        //Default public constructor is required
    }

    CategoryEntity(String id, String name, boolean following) {
        this.id = id;
        this.name = name;
        this.following = following;
    }

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
