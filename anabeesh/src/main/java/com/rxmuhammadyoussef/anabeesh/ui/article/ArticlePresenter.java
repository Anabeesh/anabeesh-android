package com.rxmuhammadyoussef.anabeesh.ui.article;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.TimelineRepo;
import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleModel;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleViewModel;
import com.rxmuhammadyoussef.anabeesh.util.diffutil.ArticleDiffCallback;
import com.rxmuhammadyoussef.core.component.activity.BaseActivityPresenter;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@ActivityScope
class ArticlePresenter extends BaseActivityPresenter {

    private final ArticleScreen articleScreen;
    private final TimelineRepo timelineRepo;
    private final ResourcesUtil resourcesUtil;
    private final ArticleMapper articleMapper;
    private final CompositeDisposable disposable;
    private final ThreadSchedulers threadSchedulers;
    private final UserSessionManager userSessionManager;
    private final BehaviorRelay<List<ArticleViewModel>> articleRelay;

    @Inject
    ArticlePresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                     @ForActivity CompositeDisposable disposable,
                     UserSessionManager userSessionManager,
                     ArticleScreen articleScreen,
                     ResourcesUtil resourcesUtil,
                     ArticleMapper articleMapper,
                     TimelineRepo timelineRepo) {
        super(articleScreen);
        this.articleScreen = articleScreen;
        this.timelineRepo = timelineRepo;
        this.articleMapper = articleMapper;
        this.threadSchedulers = threadSchedulers;
        this.userSessionManager = userSessionManager;
        this.disposable = disposable;
        this.resourcesUtil = resourcesUtil;
        this.articleRelay = BehaviorRelay.createDefault(Collections.emptyList());
    }

    @Override
    protected void onCreate() {
        articleScreen.setupToolbar();
        articleScreen.setupRefreshLayout();
        articleScreen.setupRecyclerView();
    }

    @Override
    protected void onResume() {
        fetch();
    }

    void fetch() {
        articleScreen.showLoadingAnimation();
        String userId = userSessionManager.getCurrentUser().getUserId();
        timelineRepo.fetchArticles(userId, new OperationListener<List<ArticleModel>>() {
            @Override
            public void onSuccess(List<ArticleModel> articleModelList) {
                articleScreen.hideLoadingAnimation();
                processResult(articleModelList);
            }

            @Override
            public void onError(Throwable t) {
                articleScreen.hideLoadingAnimation();
                processError(t);
            }
        });
    }

    private void processResult(List<ArticleModel> articleModels) {
        disposable.add(Single.just(articleModels)
                .map(articleMapper::toViewModels)
                .doAfterSuccess(articleRelay::accept)
                .map(newTimelineItems -> new Pair<>(articleRelay.getValue(), newTimelineItems))
                .map(ArticleDiffCallback::new)
                .map(timelineDiffCallback -> new Pair<>(DiffUtil.calculateDiff(timelineDiffCallback), timelineDiffCallback.getNewItems()))
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultNewListPair -> {
                    articleScreen.updateUi(diffResultNewListPair.first);
                    articleRelay.accept(diffResultNewListPair.second);
                }, Timber::e));
    }

    private void processError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            articleScreen.showErrorMessage(((HttpException) throwable).message());
        } else if (throwable instanceof IOException) {
            articleScreen.showErrorMessage(resourcesUtil.getString(R.string.error_network));
        } else {
            articleScreen.showErrorMessage(resourcesUtil.getString(R.string.error_communicating_with_server));
        }
    }

    List<ArticleViewModel> getArticleViewModels() {
        return articleRelay.getValue();
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
    }
}
