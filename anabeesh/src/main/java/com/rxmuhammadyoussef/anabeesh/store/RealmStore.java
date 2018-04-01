package com.rxmuhammadyoussef.anabeesh.store;

import com.rxmuhammadyoussef.anabeesh.store.model.article.ArticleEntity;
import com.rxmuhammadyoussef.anabeesh.store.model.category.CategoryEntity;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionEntity;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;

@ApplicationScope
class RealmStore {

    @Inject
    RealmStore() {
        //Required for dependency injection
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

    Single<List<CategoryEntity>> saveCategoriesAndReturnAll(List<CategoryEntity> entities) {
        return Single.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> realm.insertOrUpdate(entities));
            List<CategoryEntity> categoryEntities = copyAllCategoriesFromRealm(instance);
            instance.close();
            emitter.onSuccess(categoryEntities);
        });
    }

    Single<List<QuestionEntity>> saveQuestionsAndReturnAll(List<QuestionEntity> entities) {
        return Single.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            instance.executeTransaction(realm -> realm.insertOrUpdate(entities));
            List<QuestionEntity> categoryEntities = copyAllQuestionsFromRealm(instance);
            instance.close();
            emitter.onSuccess(categoryEntities);
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

    Single<List<CategoryEntity>> fetchCategories() {
        return Single.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            List<CategoryEntity> categoryEntities = copyAllCategoriesFromRealm(instance);
            instance.close();
            emitter.onSuccess(categoryEntities);
        });
    }

    Single<List<QuestionEntity>> fetchQuestions() {
        return Single.create(emitter -> {
            Realm instance = Realm.getDefaultInstance();
            List<QuestionEntity> categoryEntities = copyAllQuestionsFromRealm(instance);
            instance.close();
            emitter.onSuccess(categoryEntities);
        });
    }

    Completable clearDatabase() {
        return Completable.create(emitter -> {
            Realm.deleteRealm(Realm.getDefaultConfiguration());
            emitter.onComplete();
        });
    }

    private List<ArticleEntity> copyAllArticlesFromRealm(Realm instance) {
        return instance.copyFromRealm(instance
                .where(ArticleEntity.class)
                .findAll());
    }

    private List<CategoryEntity> copyAllCategoriesFromRealm(Realm instance) {
        return instance.copyFromRealm(instance
                .where(CategoryEntity.class)
                .findAll());
    }

    private List<QuestionEntity> copyAllQuestionsFromRealm(Realm instance) {
        return instance.copyFromRealm(instance
                .where(QuestionEntity.class)
                .findAll());
    }
}
