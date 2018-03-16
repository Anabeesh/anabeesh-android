package com.rxmuhammadyoussef.anabeesh.store.model.user;

import com.rxmuhammadyoussef.core.di.scope.ApplicationScope;

import javax.inject.Inject;

@ApplicationScope
public final class UserMapper {

    @Inject
    UserMapper() {
    }

    public UserEntity toEntity(UserApiResponse.DataResponse userApiResponse) {
        return new UserEntity(
                userApiResponse.getUserId(),
                userApiResponse.getFirstName(),
                userApiResponse.getLastName(),
                userApiResponse.getEmail());
    }

    public UserModel toModel(UserEntity userEntity) {
        return new UserModel(
                userEntity.getUserId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail());
    }

    public UserViewModel toViewModel(UserModel userModel) {
        return new UserViewModel(
                userModel.getUserId(),
                userModel.getFirstName() + " " + userModel.getLastName(),
                userModel.getEmail());
    }
}
