package com.rxmuhammadyoussef.anabeesh.store;

import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleModel;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.core.util.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

@ActivityScope
public class TimelineRepo {

    private final ThreadSchedulers threadSchedulers;
    private final WebServiceStore webServiceStore;
    private final CompositeDisposable disposable;
    private final ArticleMapper articleMapper;
    private final RealmStore realmStore;

    @Inject
    TimelineRepo(@IOThread ThreadSchedulers threadSchedulers,
                 @ForActivity CompositeDisposable disposable,
                 WebServiceStore webServiceStore,
                 ArticleMapper articleMapper,
                 RealmStore realmStore) {
        this.threadSchedulers = threadSchedulers;
        this.webServiceStore = webServiceStore;
        this.disposable = disposable;
        this.articleMapper = articleMapper;
        this.realmStore = realmStore;
    }

    public void fetchArticles(String userId, OperationListener<List<ArticleModel>> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener required non null");

        disposable.add(realmStore.fetchArticles()
                .map(articleMapper::toModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(operationListener::onSuccess, operationListener::onError));

        disposable.add(webServiceStore.fetchArticles(Preconditions.requireStringNotEmpty(userId))
                .map(articleMapper::toEntities)
                .flatMap(realmStore::saveArticlesAndReturnAll)
                .map(articleMapper::toModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(operationListener::onSuccess, operationListener::onError));
    }
}
