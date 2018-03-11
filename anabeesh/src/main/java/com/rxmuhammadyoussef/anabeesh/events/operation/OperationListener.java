package com.rxmuhammadyoussef.anabeesh.events.operation;

public interface OperationListener<T> {

    void onSuccess(T element);

    void onError(Throwable t);
}