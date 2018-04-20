package com.rxmuhammadyoussef.anabeesh.store;

import android.util.Pair;

import com.rxmuhammadyoussef.anabeesh.events.operation.OperationListener;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionModel;
import com.rxmuhammadyoussef.core.di.qualifier.ForActivity;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.IOThread;
import com.rxmuhammadyoussef.core.util.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

@ActivityScope
public class TimelineRepo {

    private final ThreadSchedulers threadSchedulers;
    private final WebServiceStore webServiceStore;
    private final CompositeDisposable disposable;
    private final QuestionMapper questionMapper;
    private final ArticleMapper articleMapper;
    private final RealmStore realmStore;

    @Inject
    TimelineRepo(@IOThread ThreadSchedulers threadSchedulers,
                 @ForActivity CompositeDisposable disposable,
                 WebServiceStore webServiceStore,
                 QuestionMapper questionMapper,
                 ArticleMapper articleMapper,
                 RealmStore realmStore) {
        this.threadSchedulers = threadSchedulers;
        this.webServiceStore = webServiceStore;
        this.disposable = disposable;
        this.questionMapper = questionMapper;
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

    public void fetchQuestions(String userId, OperationListener<List<QuestionModel>> operationListener) {
        Preconditions.checkNonNull(operationListener, "operationListener required non null");

        disposable.add(realmStore.fetchQuestions()
                .map(questionMapper::toModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(operationListener::onSuccess, operationListener::onError));

        disposable.add(Single.zip(
                webServiceStore.fetchTopRatedQuestions(Preconditions.requireStringNotEmpty(userId)),
                webServiceStore.fetchNewestQuestions(Preconditions.requireStringNotEmpty(userId)),
                Pair::new)
                .map(questionMapper::toEntities)
                .flatMap(realmStore::saveQuestionsAndReturnAll)
                .map(questionMapper::toModels)
                .subscribeOn(threadSchedulers.workerThread())
                .observeOn(threadSchedulers.mainThread())
                .subscribe(operationListener::onSuccess, operationListener::onError));
    }

    public Observable<List<QuestionModel>> searchQuestions(String keyword) {
        return webServiceStore.searchQuestions(
                Preconditions.requireStringNotEmpty(keyword, "you should use non empty string for"))
                .map(questionMapper::toEntities)
                .map(questionMapper::toModels);
    }
}
