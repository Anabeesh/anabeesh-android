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

    public Builder builder() {
        return new Builder()
                .id(this.id)
                .name(this.name)
                .isFollowing(this.following);
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

    public static class Builder {
        private String id;
        private String name;
        private boolean following;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder isFollowing(boolean following) {
            this.following = following;
            return this;
        }

        public CategoryEntity build() {
            return new CategoryEntity(id, name, following);
        }
    }
}
