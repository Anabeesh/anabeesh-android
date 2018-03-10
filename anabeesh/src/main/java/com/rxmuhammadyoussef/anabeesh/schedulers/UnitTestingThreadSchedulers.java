package com.rxmuhammadyoussef.anabeesh.schedulers;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 unit-testing-specific Schedulers
 */

public class UnitTestingThreadSchedulers implements ThreadSchedulers {

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler workerThread() {
        return Schedulers.newThread();
    }
}