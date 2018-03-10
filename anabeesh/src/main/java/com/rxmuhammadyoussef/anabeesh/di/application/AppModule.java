package com.rxmuhammadyoussef.anabeesh.di.application;

import android.app.Application;
import android.content.Context;

import com.rxmuhammadyoussef.core.util.PreferencesUtil;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;
import com.rxmuhammadyoussef.core.util.TextUtil;

import dagger.Module;
import dagger.Provides;

/**
 This class is responsible for providing the requested objects to {@link ApplicationScope} annotated classes
 */

@Module(includes = {SchedulersModule.class})
public class AppModule {

    private final Application application;

    public AppModule(Application application) {this.application = application;}

    @ApplicationScope
    @Provides
    Application providesApplication() {
        return application;
    }

    @ApplicationScope
    @Provides
    @ForApplication
    Context providesApplicationContext() {
        return application;
    }

    @ApplicationScope
    @Provides
    TextUtil providesTextUtil() {
        return new TextUtil(application);
    }

    @ApplicationScope
    @Provides
    PreferencesUtil providesPreferencesUtil() {
        return new PreferencesUtil(application);
    }

    @ApplicationScope
    @Provides
    ResourcesUtil providesResourcesUtil() {
        return new ResourcesUtil(application);
    }
}