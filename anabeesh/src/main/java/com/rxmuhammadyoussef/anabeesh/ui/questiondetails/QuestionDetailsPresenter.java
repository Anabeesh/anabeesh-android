package com.rxmuhammadyoussef.anabeesh.ui.questiondetails;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.store.QuestionRepo;
import com.rxmuhammadyoussef.anabeesh.store.model.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.questiondetails.QuestionDetailsMapper;
import com.rxmuhammadyoussef.anabeesh.util.diffutil.QuestionDetailsDiffCallback;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityPresenter;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@ActivityScope
class QuestionDetailsPresenter extends BaseActivityPresenter {

    private final BehaviorRelay<List<AnswerViewModel>> answerRelay;
    private final BehaviorRelay<List<TimelineItem>> timelineRelay;
    private final BehaviorRelay<QuestionViewModel> questionRelay;
    private final QuestionDetailsMapper questionDetailsMapper;
    private final ThreadSchedulers threadSchedulers;
    private final CompositeDisposable disposable;
    private final QuestionMapper questionMapper;
    private final QuestionDetailsScreen screen;
    private final AnswerMapper answerMapper;
    private final QuestionRepo questionRepo;

    @Inject
    QuestionDetailsPresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                             @ForActivity CompositeDisposable disposable,
                             QuestionDetailsMapper questionDetailsMapper,
                             QuestionMapper questionMapper,
                             QuestionDetailsScreen screen,
                             AnswerMapper answerMapper,
                             QuestionRepo questionRepo) {
        super(screen);
        this.answerRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.timelineRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.questionRelay = BehaviorRelay.create();
        this.questionDetailsMapper = questionDetailsMapper;
        this.threadSchedulers = threadSchedulers;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
        this.questionRepo = questionRepo;
        this.disposable = disposable;
        this.screen = screen;
    }

    @Override
    protected void onCreate() {
        screen.setupToolbar();
        screen.setupRecyclerView();
        QuestionDetailsActivityArgs args = QuestionDetailsActivityArgs.Companion.deserializeFrom(getIntent());
        questionRelay.accept(args.getQuestionViewModel());
        disposable.add(fetchQuestion());
        disposable.add(fetchAnswers());
        refresh();
    }

    private Disposable fetchQuestion() {
        return questionRepo.fetchQuestion(questionRelay.getValue().getId())
                .map(questionMapper::toViewModel)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(questionViewModel -> {
                    questionRelay.accept(questionViewModel);
                    refresh();
                }, this::processError);
    }

    private Disposable fetchAnswers() {
        return questionRepo.fetchAnswers(questionRelay.getValue().getId())
                .map(answerMapper::toViewModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(questionViewModel -> {
                    answerRelay.accept(questionViewModel);
                    refresh();
                }, this::processError);
    }

    private void refresh() {
        disposable.add(Single.just(new Pair<>(questionRelay.getValue(), answerRelay.getValue()))
                .map(questionDetailsMapper::toTimelineItems)
                .map(newTimelineList -> new Pair<>(timelineRelay.getValue(), newTimelineList))
                .map(QuestionDetailsDiffCallback::new)
                .map(diffCallback -> new Pair<>(DiffUtil.calculateDiff(diffCallback), diffCallback.getNewItems()))
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultListPair -> {
                    screen.updateUi(diffResultListPair.first);
                    timelineRelay.accept(diffResultListPair.second);
                }, Timber::e));
    }

    private void processError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            screen.showErrorMessage(throwable.getMessage());
            Timber.tag("MuhammadDebug").d(((HttpException) throwable).response().toString());
        } else if (throwable instanceof IOException) {
            screen.showErrorMessage(getResourcesUtil().getString(R.string.error_network));
        } else {
            screen.showErrorMessage(getResourcesUtil().getString(R.string.error_communicating_with_server));
        }
    }

    List<TimelineItem> getTimeLineItems() {
        return timelineRelay.getValue();
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
    }
}
