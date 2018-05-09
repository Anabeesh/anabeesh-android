package com.rxmuhammadyoussef.anabeesh.store.model.answer;

import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ApplicationScope
public class AnswerMapper {

    @Inject
    AnswerMapper() {
    }

    public List<AnswerEntity> toEntities(List<AnswerApiResponse> answerApiResponseList) {
        List<AnswerEntity> answerEntities = new ArrayList<>();
        for (AnswerApiResponse answerApiResponse : answerApiResponseList) {
            answerEntities.add(new AnswerEntity(
                    answerApiResponse.getId(),
                    answerApiResponse.getBody(),
                    answerApiResponse.getUserId(),
                    answerApiResponse.getQuestionId()));
        }
        return answerEntities;
    }

    public List<AnswerModel> toModels(List<AnswerEntity> answerEntities) {
        List<AnswerModel> answerModels = new ArrayList<>();
        for (AnswerEntity answerApiResponse : answerEntities) {
            answerModels.add(new AnswerModel(
                    answerApiResponse.getId(),
                    answerApiResponse.getBody(),
                    answerApiResponse.getUserId(),
                    answerApiResponse.getQuestionId()));
        }
        return answerModels;
    }

    public List<AnswerViewModel> toViewModels(List<AnswerModel> answerModels) {
        List<AnswerViewModel> answerViewModels = new ArrayList<>();
        for (AnswerModel answerApiResponse : answerModels) {
            answerViewModels.add(new AnswerViewModel(
                    answerApiResponse.getId(),
                    answerApiResponse.getBody(),
                    answerApiResponse.getUserId(),
                    answerApiResponse.getQuestionId()));
        }
        return answerViewModels;
    }
}
