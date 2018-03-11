package com.rxmuhammadyoussef.anabeesh.store.model.user;

public class UserModel {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;

    UserModel(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
