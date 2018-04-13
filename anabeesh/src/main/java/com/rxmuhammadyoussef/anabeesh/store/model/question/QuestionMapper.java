package com.rxmuhammadyoussef.anabeesh.store.model.question;

import android.util.Pair;

import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ApplicationScope
public class QuestionMapper {

    @Inject
    QuestionMapper() {
        //no extra logic needed
    }

    public List<QuestionEntity> toEntities(Pair<List<QuestionApiResponse>, List<QuestionApiResponse>> topRatedNewestPair) {
        List<QuestionEntity> entities = new ArrayList<>();
        for (QuestionApiResponse questionApiResponse : topRatedNewestPair.first) {
            entities.add(new QuestionEntity(
                    questionApiResponse.getId(),
                    questionApiResponse.getUserId(),
                    questionApiResponse.getHeadline(),
                    questionApiResponse.getDescription(),
                    questionApiResponse.getCategoryId(),
                    questionApiResponse.getNumberOfAnswers(),
                    questionApiResponse.getUpVotes(),
                    questionApiResponse.getDownVotes(),
                    true,
                    false));
        }
        for (QuestionApiResponse questionApiResponse : topRatedNewestPair.second) {
            entities.add(new QuestionEntity(
                    questionApiResponse.getId(),
                    questionApiResponse.getUserId(),
                    questionApiResponse.getHeadline(),
                    questionApiResponse.getDescription(),
                    questionApiResponse.getCategoryId(),
                    questionApiResponse.getNumberOfAnswers(),
                    questionApiResponse.getUpVotes(),
                    questionApiResponse.getDownVotes(),
                    false,
                    true));
        }
        return entities;
    }

    public List<QuestionModel> toModels(List<QuestionEntity> questionEntities) {
        List<QuestionModel> models = new ArrayList<>();
        for (QuestionEntity questionEntity : questionEntities) {
            models.add(new QuestionModel(
                    questionEntity.getId(),
                    questionEntity.getUserId(),
                    questionEntity.getHeadline(),
                    questionEntity.getDescription(),
                    questionEntity.getCategoryId(),
                    questionEntity.getNumberOfAnswers(),
                    questionEntity.getUpVotes(),
                    questionEntity.getDownVotes(),
                    questionEntity.isTopRated(),
                    questionEntity.isNewFeed()));
        }
        return models;
    }

    public List<QuestionViewModel> toViewModels(List<QuestionModel> questionModels) {
        List<QuestionViewModel> viewModels = new ArrayList<>();
        for (QuestionModel questionModel : questionModels) {
            viewModels.add(new QuestionViewModel(
                    questionModel.getId(),
                    questionModel.getUserId(),
                    questionModel.getHeadline(),
                    questionModel.getDescription(),
                    questionModel.getCategoryId(),
                    questionModel.getNumberOfAnswers(),
                    questionModel.getUpVotes(),
                    questionModel.getDownVotes(),
                    "https://source.unsplash.com/collection/400620/480x480",
                    questionModel.isTopRated(),
                    questionModel.isNewFeed()));
        }
        return viewModels;
    }
}
