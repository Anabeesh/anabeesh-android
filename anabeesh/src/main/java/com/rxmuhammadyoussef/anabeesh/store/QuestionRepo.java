package com.rxmuhammadyoussef.anabeesh.store;

import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.answer.AnswerModel;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionMapper;
import com.rxmuhammadyoussef.anabeesh.store.model.question.QuestionModel;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.QuestionRequestBody;
import com.rxmuhammadyoussef.anabeesh.store.model.requestbody.SearchRequestBody;
import com.rxmuhammadyoussef.core.di.scope.ActivityScope;
import com.rxmuhammadyoussef.core.util.Preconditions;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@ActivityScope
public class QuestionRepo {

    private final WebServiceStore webServiceStore;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final RealmStore realmStore;

    @Inject
    QuestionRepo(WebServiceStore webServiceStore,
                 QuestionMapper questionMapper,
                 AnswerMapper answerMapper,
                 RealmStore realmStore) {
        this.webServiceStore = webServiceStore;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
        this.realmStore = realmStore;
    }

    public Completable addQuestion(QuestionRequestBody requestBody) {
        Preconditions.checkNonNull(requestBody, "RequestBody required non null");
        return webServiceStore.addQuestion(requestBody)
                .toCompletable();
    }

    public Observable<List<QuestionModel>> searchQuestions(SearchRequestBody requestBody) {
        return webServiceStore.searchQuestions(
                Preconditions.requireNonNull(requestBody, "you should use non empty string for"))
                .map(questionMapper::toEntities)
                .map(questionMapper::toModels);
    }

    public Single<List<AnswerModel>> fetchAnswers(String questionId) {
        return webServiceStore.fetchAnswers(Preconditions.requireStringNotEmpty(questionId))
                .map(answerMapper::toEntities)
                .map(answerMapper::toModels);
    }

    public Single<QuestionModel> fetchQuestion(String questionId) {
        return webServiceStore.fetchQuestion(Preconditions.requireStringNotEmpty(questionId))
                .map(questionMapper::toEntity)
                .flatMap(realmStore::saveQuestionAndReturn)
                .map(questionMapper::toModel);
    }
}
