package com.rxmuhammadyoussef.anabeesh.store.model.category;

import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@ApplicationScope
public class CategoryMapper {

    @Inject
    CategoryMapper() {
        //Required for dependency injection
    }

    public List<CategoryEntity> toEntities(List<CategoryApiResponse> categoryApiResponseList) {
        List<CategoryEntity> entities = new ArrayList<>();
        for (CategoryApiResponse categoryApiResponse : categoryApiResponseList) {
            entities.add(new CategoryEntity(
                    categoryApiResponse.getId(),
                    categoryApiResponse.getName(),
                    categoryApiResponse.isFollowing()));
        }
        return entities;
    }

    public List<CategoryModel> toModels(List<CategoryEntity> categoryEntities) {
        List<CategoryModel> models = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntities) {
            models.add(new CategoryModel(
                    categoryEntity.getId(),
                    categoryEntity.getName(),
                    categoryEntity.isFollowing()));
        }
        return models;
    }

    public List<CategoryViewModel> toViewModels(List<CategoryModel> categoryModels) {
        List<CategoryViewModel> viewModels = new ArrayList<>();
        for (CategoryModel categoryModel : categoryModels) {
            viewModels.add(new CategoryViewModel(
                    categoryModel.getId(),
                    categoryModel.getName(),
                    categoryModel.isFollowing(),
                    "https://source.unsplash.com/collection/400620/480x480"));
        }
        return viewModels;
    }

    public CategoryViewModel changeFollowingstate(CategoryViewModel categoryViewModel) {
        return new CategoryViewModel(
                categoryViewModel.getId(),
                categoryViewModel.getName(),
                !categoryViewModel.isFollowing(),
                "https://source.unsplash.com/collection/400620/480x480");
    }

    public CategoryModel toModel(CategoryEntity entity) {
        return new CategoryModel(
                entity.getId(),
                entity.getName(),
                entity.isFollowing());
    }

    public CategoryViewModel toViewModel(CategoryModel model) {
        return new CategoryViewModel(
                model.getId(),
                model.getName(),
                model.isFollowing(),
                "https://source.unsplash.com/collection/400620/480x480");
    }
}
