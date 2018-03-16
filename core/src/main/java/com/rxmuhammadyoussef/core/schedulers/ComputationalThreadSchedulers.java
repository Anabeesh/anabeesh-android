package com.rxmuhammadyoussef.core.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 Heavy computation-specific Schedulers
 */

public class ComputationalThreadSchedulers implements ThreadSchedulers {

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler workerThread() {
        return Schedulers.computation();
    }
}