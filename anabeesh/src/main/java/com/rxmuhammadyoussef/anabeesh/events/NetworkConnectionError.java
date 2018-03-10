package com.rxmuhammadyoussef.anabeesh.events;

public class NetworkConnectionError extends Throwable {

    public NetworkConnectionError() {
    }

    public NetworkConnectionError(String message) {
        super(message);
    }

    public NetworkConnectionError(String message, Throwable cause) {
        super(message, cause);
    }

    public NetworkConnectionError(Throwable cause) {
        super(cause);
    }
}
