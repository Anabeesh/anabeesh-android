package com.rxmuhammadyoussef.anabeesh.store.model.answer;

public class AnswerViewModel {

    private final String id;
    private final String text;
    private final String userId;
    private final String questionId;

    AnswerViewModel(String id, String text, String userId, String questionId) {
        this.id = id;
        this.text = text;
        this.userId = userId;
        this.questionId = questionId;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUserId() {
        return userId;
    }

    public String getQuestionId() {
        return questionId;
    }
}
