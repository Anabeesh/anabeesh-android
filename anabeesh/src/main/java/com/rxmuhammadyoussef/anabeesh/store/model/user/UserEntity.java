package com.rxmuhammadyoussef.anabeesh.store.model.user;

public class UserEntity {

    private final String userId;
    private final String firstName;
    private final String lastName;
    private final String email;

    UserEntity(String userId, String firstName, String lastName, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    String getUserId() {
        return userId;
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName() {
        return lastName;
    }

    String getEmail() {
        return email;
    }
}
