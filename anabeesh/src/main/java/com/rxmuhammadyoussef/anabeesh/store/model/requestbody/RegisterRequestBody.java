package com.rxmuhammadyoussef.anabeesh.store.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class RegisterRequestBody {

    @SerializedName("Email")
    private final String email;
    @SerializedName("Password")
    private final String password;
    @SerializedName("FirstName")
    private final String firstName;
    @SerializedName("LastName")
    private final String lastName;

    private RegisterRequestBody(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static class Builder {

        private String email;
        private String password;
        private String firstName;
        private String lastName;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public RegisterRequestBody build() {
            return new RegisterRequestBody(email, password, firstName, lastName);
        }
    }
}
