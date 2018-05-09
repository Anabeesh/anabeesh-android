package com.rxmuhammadyoussef.anabeesh.store.model.answer;

public class AnswerModel {

    private final String id;
    private final String body;
    private final String userId;
    private final String questionId;

    public AnswerModel(String id, String body, String userId, String questionId) {
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
