package com.rxmuhammadyoussef.anabeesh.store.model.user;

public class UserViewModel {

    private final String userId;
    private final String displayName;
    private final String email;

    UserViewModel(String userId, String displayName, String email) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}
