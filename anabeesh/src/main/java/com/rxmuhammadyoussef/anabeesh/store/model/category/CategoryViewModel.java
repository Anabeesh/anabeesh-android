package com.rxmuhammadyoussef.anabeesh.store.model.category;

public class CategoryViewModel {

    private final String id;
    private final String name;
    private final boolean following;
    private final String imageUrl;

    CategoryViewModel(String id, String name, boolean following, String imageUrl) {
        this.id = id;
        this.name = name;
        this.following = following;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if (obj instanceof CategoryViewModel) {
            CategoryViewModel gameModel = (CategoryViewModel) obj;
            isEqual = gameModel.getId().contains(this.id)
                    && gameModel.getName().contentEquals(this.name)
                    && gameModel.isFollowing() == this.following;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return name.length() + id.length();
    }
}
