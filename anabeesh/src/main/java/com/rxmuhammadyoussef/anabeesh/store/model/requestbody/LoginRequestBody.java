package com.rxmuhammadyoussef.anabeesh.store.model.requestbody;

import com.google.gson.annotations.SerializedName;

public class LoginRequestBody {

    @SerializedName("Email")
    private final String email;
    @SerializedName("Password")
    private final String password;

    private LoginRequestBody(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static class Builder {

        private String email;
        private String password;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public LoginRequestBody build() {
            return new LoginRequestBody(email, password);
        }
    }
}
