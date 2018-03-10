package com.rxmuhammadyoussef.anabeesh.schedulers.qualifires;

import com.rxmuhammadyoussef.anabeesh.schedulers.ThreadSchedulers;

import javax.inject.Qualifier;

/**
 This qualifier is used for distinguishing between different {@link ThreadSchedulers} for dependency injection.
 */

@Qualifier
public @interface IOThread {
}