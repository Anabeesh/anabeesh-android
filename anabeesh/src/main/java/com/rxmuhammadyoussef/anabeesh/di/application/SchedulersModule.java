package com.rxmuhammadyoussef.anabeesh.di.application;

import com.rxmuhammadyoussef.anabeesh.schedulers.ComputationalThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.IOThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.MainThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.UnitTestingThreadSchedulers;
import com.rxmuhammadyoussef.anabeesh.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.anabeesh.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.anabeesh.schedulers.qualifires.MainThread;
import com.rxmuhammadyoussef.anabeesh.schedulers.qualifires.UnitTestingThread;

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
    @UnitTestingThread
    ThreadSchedulers providesUnitTestingThreadSchedulers() {
        return new UnitTestingThreadSchedulers();
    }
}