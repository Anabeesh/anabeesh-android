package com.rxmuhammadyoussef.anabeesh.di.application;

import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.schedulers.ComputationalThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.IOThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.MainThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.TestThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.core.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.core.schedulers.qualifires.MainThread;
import com.rxmuhammadyoussef.core.schedulers.qualifires.TestThread;

import dagger.Module;
import dagger.Provides;

/**
 This class is responsible for providing the requested objects for {@link ThreadSchedulers} objects
 */

@Module
public class SchedulersModule {

    @ApplicationScope
    @Provides
    @MainThread
    ThreadSchedulers providesMainThreadSchedulers() {
        return new MainThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @IOThread
    ThreadSchedulers providesIOThreadSchedulers() {
        return new IOThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @ComputationalThread
    ThreadSchedulers providesComputationalThreadSchedulers() {
        return new ComputationalThreadSchedulers();
    }

    @ApplicationScope
    @Provides
    @TestThread
    ThreadSchedulers providesUnitTestingThreadSchedulers() {
        return new TestThreadSchedulers();
    }
}