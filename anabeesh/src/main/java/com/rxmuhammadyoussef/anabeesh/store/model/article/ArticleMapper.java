package com.rxmuhammadyoussef.anabeesh.store.model.article;

import com.rxmuhammadyoussef.anabeesh.store.api.APIsUtil;
import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ApplicationScope
public final class ArticleMapper {

    @Inject
    ArticleMapper() {
        //no extra logic needed
    }

    public List<ArticleEntity> toEntities(List<ArticleApiResponse> articleApiResponses) {
        List<ArticleEntity> articleEntities = new ArrayList<>();
        for (ArticleApiResponse articleApiResponse : articleApiResponses) {
            articleEntities.add(toEntity(articleApiResponse));
        }
        return articleEntities;
    }

    private ArticleEntity toEntity(ArticleApiResponse articleApiResponse) {
        return new ArticleEntity(
                articleApiResponse.getId(),
                articleApiResponse.getHeading(),
                articleApiResponse.getBody(),
                articleApiResponse.getCategoryId(),
                articleApiResponse.getUserId(),
                articleApiResponse.getUserName(),
                articleApiResponse.getUserAvatarUrl());
    }

    public List<ArticleModel> toModels(List<ArticleEntity> articleEntities) {
        List<ArticleModel> articleModels = new ArrayList<>();
        for (ArticleEntity articleApiResponse : articleEntities) {
            articleModels.add(toModel(articleApiResponse));
        }
        return articleModels;
    }

    private ArticleModel toModel(ArticleEntity articleEntity) {
        return new ArticleModel(
                articleEntity.getId(),
                articleEntity.getHeading(),
                articleEntity.getBody(),
                articleEntity.getCategoryId(),
                articleEntity.getUserId(),
                articleEntity.getUserName(),
                articleEntity.getUserAvatarUrl());
    }

    public List<ArticleViewModel> toViewModels(List<ArticleModel> articleModels) {
        List<ArticleViewModel> articleViewModels = new ArrayList<>();
        for (ArticleModel articleApiResponse : articleModels) {
            articleViewModels.add(toViewModel(articleApiResponse));
        }
        return articleViewModels;
    }

    private ArticleViewModel toViewModel(ArticleModel articleApiResponse) {
        return new ArticleViewModel(
                articleApiResponse.getId(),
                articleApiResponse.getHeading(),
                articleApiResponse.getBody(),
                articleApiResponse.getCategoryId(),
                articleApiResponse.getUserId(),
                articleApiResponse.getUserName(),
                APIsUtil.BASE_URL.concat(articleApiResponse.getUserAvatarUrl()),
                "https://source.unsplash.com/collection/400620/480x480");
    }
}
