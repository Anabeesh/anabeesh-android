package com.rxmuhammadyoussef.anabeesh.store.model.user;

import com.google.gson.annotations.SerializedName;

public class UserApiResponse {

    public static class DataResponse {
        @SerializedName("UserId")
        private String userId;

        @SerializedName("FirstName")
        private String firstName;

        @SerializedName("LastName")
        private String lastName;

        @SerializedName("Email")
        private String email;

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

    public static class ErrorResponse {

        @SerializedName("Message")
        private String errorMessage;

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
