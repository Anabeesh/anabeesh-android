package com.rxmuhammadyoussef.anabeesh.store;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleEntity;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;
import com.rxmuhammadyoussef.core.schedulers.ThreadSchedulers;
import com.rxmuhammadyoussef.core.schedulers.qualifires.IOThread;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.annotations.SchedulerSupport;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.rx.CollectionChange;

@ApplicationScope
class RealmStore {

    private final ThreadSchedulers threadSchedulers;

    @Inject
    RealmStore(@IOThread ThreadSchedulers threadSchedulers) {
        this.threadSchedulers = threadSchedulers;
    }

    Single<List<ArticleEntity>> saveArticlesAndReturnAll(List<ArticleEntity> entities) {
        return Single.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> realm.insertOrUpdate(entities));
            List<ArticleEntity> articleEntities = copyAllArticlesFromRealm(instance);
            instance.close();
            emitter.onSuccess(articleEntities);
        });
    }

    Single<List<ArticleEntity>> fetchArticles() {
        return Single.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            List<ArticleEntity> articleEntities = copyAllArticlesFromRealm(instance);
            instance.close();
            emitter.onSuccess(articleEntities);
        });
    }

    @SchedulerSupport(SchedulerSupport.IO)
    Observable<List<ArticleEntity>> observeArticles() {
        return Observable.just(ArticleEntity.class)
                .map(Realm.getDefaultInstance()::where)
                .map(RealmQuery::findAll)
                .flatMap(RealmResults::asChangesetObservable)
                .map(CollectionChange::getCollection)
                .map(Realm.getDefaultInstance()::copyFromRealm)
                .doAfterTerminate(Realm.getDefaultInstance()::close)
                .subscribeOn(threadSchedulers.workerThread());
    }

    private List<ArticleEntity> copyAllArticlesFromRealm(Realm instance) {
        return instance.copyFromRealm(instance
                .where(ArticleEntity.class)
                .findAll());
    }
}
