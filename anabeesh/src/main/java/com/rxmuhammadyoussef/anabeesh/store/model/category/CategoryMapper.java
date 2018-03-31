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
                    categoryModel.isFollowing()));
        }
        return viewModels;
    }

    public List<CategoryModel> getList() {
        List<CategoryModel> categoryModels = new ArrayList<>();
        categoryModels.add(new CategoryModel("id1", "Title goes here", false));
        categoryModels.add(new CategoryModel("id2", "Title goes here", true));
        categoryModels.add(new CategoryModel("id3", "Title goes here", true));
        categoryModels.add(new CategoryModel("id4", "Title goes here", false));
        categoryModels.add(new CategoryModel("id5", "Title goes here", false));
        categoryModels.add(new CategoryModel("id1", "Title goes here", false));
        categoryModels.add(new CategoryModel("id2", "Title goes here", true));
        categoryModels.add(new CategoryModel("id3", "Title goes here", true));
        categoryModels.add(new CategoryModel("id4", "Title goes here", false));
        categoryModels.add(new CategoryModel("id5", "Title goes here", false));
        categoryModels.add(new CategoryModel("id1", "Title goes here", false));
        categoryModels.add(new CategoryModel("id2", "Title goes here", true));
        categoryModels.add(new CategoryModel("id3", "Title goes here", true));
        categoryModels.add(new CategoryModel("id4", "Title goes here", false));
        categoryModels.add(new CategoryModel("id5", "Title goes here", false));
        categoryModels.add(new CategoryModel("id1", "Title goes here", false));
        categoryModels.add(new CategoryModel("id2", "Title goes here", true));
        categoryModels.add(new CategoryModel("id3", "Title goes here", true));
        categoryModels.add(new CategoryModel("id4", "Title goes here", false));
        categoryModels.add(new CategoryModel("id5", "Title goes here", false));
        return categoryModels;
    }
}
