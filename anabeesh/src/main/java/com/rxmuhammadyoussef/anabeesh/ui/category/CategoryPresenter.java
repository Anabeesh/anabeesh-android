package com.rxmuhammadyoussef.anabeesh.ui.category;

import android.support.v7.util.DiffUtil;
import android.util.Pair;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.rxmuhammadyoussef.anabeesh.R;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationAnimationListener;
import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.InterestsRepo;
import com.rxmuhammadyoussef.anabeesh.store.UserSessionManager;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryModel;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryViewModel;
import com.rxmuhammadyoussef.anabeesh.util.diffutil.CategoriesDiffCallback;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.ComputationalThread;
import com.rxmuhammadyoussef.core.util.ResourcesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@FragmentScope
class CategoryPresenter {

    private final CategoryScreen categoryScreen;
    private final InterestsRepo interestsRepo;
    private final CategoryMapper categoryMapper;
    private final ResourcesUtil resourcesUtil;
    private final CompositeDisposable disposable;
    private final ThreadSchedulers threadSchedulers;
    private final UserSessionManager userSessionManager;
    private final BehaviorRelay<List<CategoryViewModel>> categoryRelay;

    @Inject
    CategoryPresenter(@ComputationalThread ThreadSchedulers threadSchedulers,
                      @ForFragment CompositeDisposable disposable,
                      UserSessionManager userSessionManager,
                      CategoryScreen categoryScreen,
                      CategoryMapper categoryMapper,
                      InterestsRepo interestsRepo,
                      ResourcesUtil resourcesUtil) {
        this.categoryScreen = categoryScreen;
        this.interestsRepo = interestsRepo;
        this.categoryMapper = categoryMapper;
        this.threadSchedulers = threadSchedulers;
        this.userSessionManager = userSessionManager;
        this.disposable = disposable;
        this.resourcesUtil = resourcesUtil;
        this.categoryRelay = BehaviorRelay.createDefault(Collections.emptyList());
    }

    void onViewCreated() {
        categoryScreen.setupRecyclerView();
        categoryScreen.setupRefreshLayout();
    }

    void onResume() {
        fetch();
    }

    void fetch() {
        categoryScreen.showLoadingAnimation();
        String userId = userSessionManager.getCurrentUser().getUserId();
        interestsRepo.fetchCategories(userId, new OperationListener<List<CategoryModel>>() {
            @Override
            public void onSuccess(List<CategoryModel> articleModelList) {
                categoryScreen.hideLoadingAnimation();
                processResult(articleModelList);
            }

            @Override
            public void onError(Throwable t) {
                categoryScreen.hideLoadingAnimation();
                processError(t);
            }
        });
    }

    void followOrUnFollowCategory(int adapterPosition, CategoryViewModel categoryViewModel, OperationAnimationListener<CategoryViewModel> operationListener) {
        disposable.add(interestsRepo
                .followOrUnFollowCategory(categoryViewModel.isFollowing(), userSessionManager.getCurrentUser().getUserId(), categoryViewModel.getId())
                .map(categoryMapper::toViewModel)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .doOnSubscribe(ignored -> operationListener.onPreExecute())
                .doFinally(operationListener::onPostExecute)
                .subscribe(viewModel -> {
                    List<CategoryViewModel> newList = new ArrayList<>();
                    newList.addAll(categoryRelay.getValue());
                    newList.remove(adapterPosition);
                    newList.add(adapterPosition, viewModel);
                    categoryRelay.accept(newList);
                    categoryScreen.notifyCategoryChanged(adapterPosition);
                }, this::processError));
    }

    private void processResult(List<CategoryModel> categoryModels) {
        disposable.add(Single.just(categoryModels)
                .map(categoryMapper::toViewModels)
                .map(newTimelineItems -> new Pair<>(categoryRelay.getValue(), newTimelineItems))
                .map(CategoriesDiffCallback::new)
                .map(timelineDiffCallback -> new Pair<>(DiffUtil.calculateDiff(timelineDiffCallback), timelineDiffCallback.getNewItems()))
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(diffResultNewListPair -> {
                    categoryScreen.updateUi(diffResultNewListPair.first);
                    categoryRelay.accept(diffResultNewListPair.second);
                }, Timber::e));
    }

    private void processError(Throwable throwable) {
        Timber.e(throwable);
        if (throwable instanceof HttpException) {
            categoryScreen.showErrorMessage(((HttpException) throwable).message());
        } else if (throwable instanceof IOException) {
            categoryScreen.showErrorMessage(resourcesUtil.getString(R.string.error_network));
        } else {
            categoryScreen.showErrorMessage(resourcesUtil.getString(R.string.error_communicating_with_server));
        }
    }

    List<CategoryViewModel> getCategoryViewModels() {
        return categoryRelay.getValue();
    }
}
