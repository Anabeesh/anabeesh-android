package com.rxmuhammadyoussef.anabeesh.di.activity;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;

import com.rxmuhammadyoussef.anabeesh.ui.article.ArticleScreen;
import com.rxmuhammadyoussef.anabeesh.ui.articledetails.ArticleDetailsScreen;
import com.rxmuhammadyoussef.anabeesh.ui.host.HostActivityScreen;
import com.rxmuhammadyoussef.anabeesh.ui.login.LoginActivityScreen;
import com.rxmuhammadyoussef.anabeesh.ui.questiondetails.QuestionDetailsScreen;
import com.rxmuhammadyoussef.anabeesh.ui.registration.RegistrationActivityScreen;
import com.rxmuhammadyoussef.core.component.activity.BaseActivity;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.util.UiUtil;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 This class is responsible for providing the requested objects to {@link ActivityScope} annotated classes
 */

@Module
public class ActivityModule {

    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {this.activity = activity;}

    @ActivityScope
    @Provides
    BaseActivity provideActivity() {
        return activity;
    }

    @ActivityScope
    @Provides
    @ForActivity
    Context provideActivityContext() {
        return activity;
    }

    @ActivityScope
    @ForActivity
    @Provides
    Lifecycle provideLifCycle() {
        return activity.getLifecycle();
    }

    @ActivityScope
    @Provides
    UiUtil providesUiUtil() {
        return new UiUtil(activity);
    }

    @ActivityScope
    @ForActivity
    @Provides
    CompositeDisposable providesCompositeDisposable() {
        return new CompositeDisposable();
    }

    @ActivityScope
    @Provides
    LoginActivityScreen providesLoginScreen() {
        return (LoginActivityScreen) activity;
    }

    @ActivityScope
    @Provides
    RegistrationActivityScreen providesRegistrationScreen() {
        return (RegistrationActivityScreen) activity;
    }

    @ActivityScope
    @Provides
    HostActivityScreen providesHostScreen() {
        return (HostActivityScreen) activity;
    }

    @ActivityScope
    @Provides
    ArticleScreen providesArticleScreen() {
        return (ArticleScreen) activity;
    }

    @ActivityScope
    @Provides
    ArticleDetailsScreen providesArticleDetailsScreen() {
        return (ArticleDetailsScreen) activity;
    }

    @ActivityScope
    @Provides
    QuestionDetailsScreen providesQuestionDetailsScreen() {
        return (QuestionDetailsScreen) activity;
    }
}