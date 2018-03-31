package com.rxmuhammadyoussef.anabeesh.store;

import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryModel;
import com.rxmuhammadyoussef.core.di.qualifier.ForFragment;
import com.rxmuhammadyoussef.core.di.scope.FragmentScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.core.util.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@FragmentScope
public class InterestsRepo {

    private final ThreadSchedulers threadSchedulers;
    private final WebServiceStore webServiceStore;
    private final CompositeDisposable disposable;
    private final CategoryMapper categoryMapper;
    private final RealmStore realmStore;

    @Inject
    InterestsRepo(@IOThread ThreadSchedulers threadSchedulers,
                  @ForFragment CompositeDisposable disposable,
                  WebServiceStore webServiceStore,
                  CategoryMapper categoryMapper,
                  RealmStore realmStore) {
        this.threadSchedulers = threadSchedulers;
        this.webServiceStore = webServiceStore;
        this.disposable = disposable;
        this.categoryMapper = categoryMapper;
        this.realmStore = realmStore;
    }

    public void fetchCategories(String userId, OperationListener<List<CategoryModel>> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener required non null");

        disposable.add(realmStore.fetchCategories()
                .map(categoryMapper::toModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(operationListener::onSuccess, operationListener::onError));

        disposable.add(webServiceStore.fetchCategories(Preconditions.requireStringNotEmpty(userId))
                .map(categoryMapper::toEntities)
                .flatMap(realmStore::saveCategoriesAndReturnAll)
                .map(categoryMapper::toModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(operationListener::onSuccess, operationListener::onError));
    }
}
