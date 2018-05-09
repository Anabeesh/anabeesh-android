package com.rxmuhammadyoussef.anabeesh.ui.addquestion;

import android.util.Pair;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.store.QuestionRepo;
import com.rxmuhammadyoussef.anabeesh.store.TimelineRepo;
import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.QuestionRequestBody;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityPresenter;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.core.util.TextUtil;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@ActivityScope
class AddQuestionPresenter extends BaseActivityPresenter {

    private final BehaviorRelay<Boolean> titleValidityRelay;
    private final BehaviorRelay<Boolean> bodyValidityRelay;
    private final UserSessionManager userSessionManager;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposable;
    private final QuestionRepo questionRepo;
    private final AddQuestionScreen screen;

    @Inject
    AddQuestionPresenter(UserSessionManager userSessionManager, @ComputationalThread ThreadSchedulers threadSchedulers,
                         @ForActivity CompositeDisposable disposable,
                         QuestionRepo questionRepo,
                         AddQuestionScreen screen) {
        super(screen);
        this.questionRepo = questionRepo;
        this.titleValidityRelay = BehaviorRelay.createDefault(false);
        this.bodyValidityRelay = BehaviorRelay.createDefault(false);
        this.userSessionManager = userSessionManager;
        this.threadSchedulers = threadSchedulers;
        this.disposable = disposable;
        this.screen = screen;
    }

    @Override
    protected void onCreate() {
        screen.setupEditText();
        disposable.add(Observable.combineLatest(
                titleValidityRelay.hide(),
                bodyValidityRelay.hide(),
                Pair::new)
                .map(booleanBooleanPair -> booleanBooleanPair.first && booleanBooleanPair.second)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(screen::setSendButtonEnabled, Timber::e));
    }

    void onAfterTitleChanged(TextUtil.Result result) {
        titleValidityRelay.accept(result.isValid());
    }

    void onAfterBodyChanged(TextUtil.Result result) {
        bodyValidityRelay.accept(result.isValid());
    }

    void postQuestion(String title, String body) {
        QuestionRequestBody requestBody = new QuestionRequestBody(title, body, userSessionManager.getCurrentUser().getUserId(), "1");
        disposable.add(questionRepo.addQuestion(requestBody)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .doOnSubscribe(ignored -> screen.showLoadingAnimation())
                .doFinally(screen::hideLoadingAnimation)
                .subscribe(screen::onQuestionPostedSuccessfully, this::processError));
    }

    private void processError(Throwable throwable) {
        Timber.e(throwable);
        if (throwable instanceof HttpException) {
            screen.showErrorMessage(((HttpException) throwable).message());
        } else if (throwable instanceof IOException) {
            screen.showErrorMessage(getResourcesUtil().getString(R.string.error_network));
        } else {
            screen.showErrorMessage(getResourcesUtil().getString(R.string.error_communicating_with_server));
        }
    }
}
