package com.rxmuhammadyoussef.anabeesh.ui.home;

import android.support.v7.util.DiffUtil;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.TimelineRepo;
import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleModel;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimeLineItemMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.util.diffutil.TimelineDiffCallback;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@FragmentScope
class HomePresenter {

    private final HomeScreen homeScreen;
    private final TimelineRepo timelineRepo;
    private final ArticleMapper articleMapper;
    private final ResourcesUtil resourcesUtil;
    private final QuestionMapper questionMapper;
    private final CompositeDisposable disposable;
    private final ThreadSchedulers threadSchedulers;
    private final TimeLineItemMapper timeLineItemMapper;
    private final UserSessionManager userSessionManager;
    private final BehaviorRelay<List<ArticleViewModel>> articleRelay;
    private final BehaviorRelay<List<QuestionViewModel>> questionRelay;
    private final BehaviorRelay<List<TimelineItem>> timelineItemRelay;

    @Inject
    HomePresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                  @ForFragment CompositeDisposable disposable,
                  TimeLineItemMapper timeLineItemMapper,
                  UserSessionManager userSessionManager,
                  QuestionMapper questionMapper,
                  ResourcesUtil resourcesUtil,
                  ArticleMapper articleMapper,
                  TimelineRepo timelineRepo,
                  HomeScreen homeScreen) {
        this.threadSchedulers = threadSchedulers;
        this.homeScreen = homeScreen;
        this.timelineRepo = timelineRepo;
        this.articleMapper = articleMapper;
        this.timeLineItemMapper = timeLineItemMapper;
        this.userSessionManager = userSessionManager;
        this.disposable = disposable;
        this.resourcesUtil = resourcesUtil;
        this.questionMapper = questionMapper;
        this.timelineItemRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.articleRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.questionRelay = BehaviorRelay.createDefault(Collections.emptyList());
    }

    void onViewCreated() {
        homeScreen.setupRecyclerView();
        homeScreen.setupRefreshLayout();
    }

    void onResume() {
        fetch();
    }

    void fetch() {
        homeScreen.showLoadingAnimation();
        String userId = userSessionManager.getCurrentUser().getUserId();
        timelineRepo.fetchArticles(userId, new OperationListener<List<ArticleModel>>() {
            @Override
            public void onSuccess(List<ArticleModel> articleModelList) {
                homeScreen.hideLoadingAnimation();
                processArticleResult(articleModelList);
            }

            @Override
            public void onError(Throwable t) {
                homeScreen.hideLoadingAnimation();
                processError(t);
            }
        });
        timelineRepo.fetchQuestions(userId, new OperationListener<List<QuestionModel>>() {
            @Override
            public void onSuccess(List<QuestionModel> questionModels) {
                homeScreen.hideLoadingAnimation();
                processQuestionResult(questionModels);
            }

            @Override
            public void onError(Throwable t) {
                homeScreen.hideLoadingAnimation();
                processError(t);
            }
        });
    }

    void onAfterSearchChanged(InitialValueObservable<TextViewAfterTextChangeEvent> afterSearchChangedObservable) {
        disposable.add(afterSearchChangedObservable
                .map(TextViewAfterTextChangeEvent::editable)
                .map(Editable::toString)
                .map(String::trim)
                .map(this::getAppropriateKeyWord)
                .debounce(300, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .skip(1)
                .flatMap(timelineRepo::searchQuestions)
                .map(questionMapper::toViewModels)
                .map(timeLineItemMapper::toTimelineItems)
                .map(newTimelineItems -> new Pair<>(timelineItemRelay.getValue(), newTimelineItems))
                .map(TimelineDiffCallback::new)
                .map(timelineDiffCallback -> new Pair<>(DiffUtil.calculateDiff(timelineDiffCallback), timelineDiffCallback.getNewItems()))
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultNewListPair -> {
                    homeScreen.updateUi(diffResultNewListPair.first);
                    timelineItemRelay.accept(diffResultNewListPair.second);
                }, Timber::e));
    }

    private String getAppropriateKeyWord(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return "EmptyUnProcessableStringWorksAsAPlaceHolderForEmptyKeywords";
        } else {
            return keyword;
        }
    }

    private void processArticleResult(List<ArticleModel> articleModels) {
        disposable.add(Single.just(articleModels)
                .map(articleMapper::toViewModels)
                .doOnSuccess(ignored -> Log.d("MuhammadDebug", "raw"+String.valueOf(ignored.size())))
                .doOnSuccess(ignored -> Log.d("MuhammadDebug", "relay before"+String.valueOf(articleRelay.getValue().size())))
                .doOnSuccess(articleRelay::accept)
                .doOnSuccess(ignored -> Log.d("MuhammadDebug", "relay after"+String.valueOf(articleRelay.getValue().size())))
                .map(articleViewModels -> timeLineItemMapper.toTimelineItems(articleRelay.getValue(), questionRelay.getValue()))
                .map(newTimelineItems -> new Pair<>(timelineItemRelay.getValue(), newTimelineItems))
                .map(TimelineDiffCallback::new)
                .map(timelineDiffCallback -> new Pair<>(DiffUtil.calculateDiff(timelineDiffCallback), timelineDiffCallback.getNewItems()))
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultNewListPair -> {
                    homeScreen.updateUi(diffResultNewListPair.first);
                    timelineItemRelay.accept(diffResultNewListPair.second);
                }, Timber::e));
    }

    private void processQuestionResult(List<QuestionModel> questionModels) {
        disposable.add(Single.just(questionModels)
                .map(questionMapper::toViewModels)
                .doOnSuccess(questionRelay::accept)
                .map(articleViewModels -> timeLineItemMapper.toTimelineItems(articleRelay.getValue(), questionRelay.getValue()))
                .map(newTimelineItems -> new Pair<>(timelineItemRelay.getValue(), newTimelineItems))
                .map(TimelineDiffCallback::new)
                .map(timelineDiffCallback -> new Pair<>(DiffUtil.calculateDiff(timelineDiffCallback), timelineDiffCallback.getNewItems()))
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultNewListPair -> {
                    homeScreen.updateUi(diffResultNewListPair.first);
                    timelineItemRelay.accept(diffResultNewListPair.second);
                }, Timber::e));
    }

    private void processError(Throwable throwable) {
        Timber.e(throwable);
        if (throwable instanceof HttpException) {
            homeScreen.showErrorMessage(((HttpException) throwable).message());
            Timber.tag("MuhammadDebug").d(((HttpException) throwable).response().toString());
        } else if (throwable instanceof IOException) {
            homeScreen.showErrorMessage(resourcesUtil.getString(R.string.error_network));
        } else {
            homeScreen.showErrorMessage(resourcesUtil.getString(R.string.error_communicating_with_server));
        }
    }

    List<TimelineItem> getTimeLineItems() {
        return timelineItemRelay.getValue();
    }

    List<ArticleViewModel> getArticleViewModels() {
        return articleRelay.getValue();
    }
}
