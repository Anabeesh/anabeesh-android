package com.rxmuhammadyoussef.anabeesh.store.model.category;

public class CategoryViewModel {

    private final String id;
    private final String name;
    private final boolean following;

    CategoryViewModel(String id, String name, boolean following) {
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
