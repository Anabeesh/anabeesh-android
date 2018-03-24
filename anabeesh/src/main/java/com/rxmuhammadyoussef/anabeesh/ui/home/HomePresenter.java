package com.rxmuhammadyoussef.anabeesh.ui.home;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.events.error.NetworkConnectionError;
import com.rxmuhammadyoussef.anabeesh.events.error.WebServiceError;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.TimelineRepo;
import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleModel;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimeLineItemMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.timeline.TimelineItem;
import com.rxmuhammadyoussef.anabeesh.util.TimelineDiffCallback;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;

import java.util.Collections;
import java.util.List;

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
    private final CompositeDisposable disposable;
    private final ThreadSchedulers threadSchedulers;
    private final TimeLineItemMapper timeLineItemMapper;
    private final UserSessionManager userSessionManager;
    private final BehaviorRelay<List<ArticleViewModel>> articleRelay;
    private final BehaviorRelay<List<TimelineItem>> timelineItemRelay;

    @Inject
    HomePresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                  @ForFragment CompositeDisposable disposable,
                  TimeLineItemMapper timeLineItemMapper,
                  UserSessionManager userSessionManager,
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
        this.timelineItemRelay = BehaviorRelay.createDefault(Collections.emptyList());
        this.articleRelay = BehaviorRelay.createDefault(Collections.emptyList());
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
                processResult(articleModelList);
            }

            @Override
            public void onError(Throwable t) {
                homeScreen.hideLoadingAnimation();
                processError(t);
            }
        });
    }

    private void processResult(List<ArticleModel> articleModels) {
        disposable.add(Single.just(articleModels)
                .map(articleMapper::toViewModels)
                .doAfterSuccess(articleRelay::accept)
                .map(timeLineItemMapper::toTimelineItems)
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
        if (throwable instanceof WebServiceError) {
            homeScreen.showErrorMessage(throwable.getMessage());
        } else if (throwable instanceof NetworkConnectionError) {
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
