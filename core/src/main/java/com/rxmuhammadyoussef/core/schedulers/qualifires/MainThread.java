package com.rxmuhammadyoussef.core.schedulers.qualifires;

import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;

import javax.inject.Qualifier;

/**
 This qualifier is used for distinguishing between different {@link ThreadSchedulers} for dependency injection.
 */

@Qualifier
public @interface MainThread {
}