package com.rxmuhammadyoussef.anabeesh;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.rxmuhammadyoussef.anabeesh.di.application.AppComponent;
import com.rxmuhammadyoussef.anabeesh.di.application.AppModule;
import com.rxmuhammadyoussef.anabeesh.di.application.DaggerAppComponent;
import com.rxmuhammadyoussef.core.util.FontUtil;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

public class AnabeeshApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FontUtil.overrideDefaultMonoSpaceFont(getAssets(), "NeoSansArabic.ttf");
        setStrictModeEnabledForDebug();
        setupTimberTree();
        installLeakCanary();
    }

    private final AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();

    public static AppComponent getComponent(Context context) {
        return getApp(context).appComponent;
    }

    //This is a hack to get a non-static field from a static method (i.e. appComponent)
    private static AnabeeshApplication getApp(Context context) {
        return (AnabeeshApplication) context.getApplicationContext();
    }

    /*
     * When enabled it detects things you might be doing by accident and brings them to your attention so you can fix them.
     * Thread policy is used to catch accidental disk or network operations on the application's MAIN thread.
     * VmPolicy is used to detect when a closeable or other object with an explicit termination method is finalized without having been closed.
     */
    private void setStrictModeEnabledForDebug() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
    }

    private void setupTimberTree() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        //TODO setup different tree for release (if needed)
    }

    private void installLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}