package com.rxmuhammadyoussef.anabeesh.events;

public class WebServiceError extends Throwable {

    public WebServiceError() {
    }

    public WebServiceError(String message) {
        super(message);
    }

    public WebServiceError(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServiceError(Throwable cause) {
        super(cause);
    }
}
