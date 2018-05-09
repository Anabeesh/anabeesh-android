package com.rxmuhammadyoussef.anabeesh.store.model.answer;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class AnswerEntity extends RealmObject {

    @PrimaryKey
    private String id;
    private String body;
    private String userId;
    private String questionId;

    public AnswerEntity() {
    }

    AnswerEntity(String id, String body, String userId, String questionId) {
        this.id = id;
        this.body = body;
        this.userId = userId;
        this.questionId = questionId;
    }

    String getId() {
        return id;
    }

    String getBody() {
        return body;
    }

    String getUserId() {
        return userId;
    }

    String getQuestionId() {
        return questionId;
    }
}
