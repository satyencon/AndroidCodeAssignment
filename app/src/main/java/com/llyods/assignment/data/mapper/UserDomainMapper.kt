package com.llyods.assignment.data.mapper

import com.llyods.assignment.data.remote.responses.UserDetailResponse
import com.llyods.assignment.data.remote.responses.UserListResponse
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.datamodel.UserModel
import javax.inject.Inject


class UserDomainMapper @Inject constructor() {

    fun map(userListResponse: UserListResponse): List<UserModel> {
        return userListResponse.map { userResponse ->
            with(userResponse) {
                UserModel(
                    avatar_url = avatar_url,
                    id = id,
                    login = login,
                    type = type,
                    url = url
                ) }
        }
    }

    fun mapperDetail(userDetailModel: UserDetailResponse): UserDetailModel {
        return  with(userDetailModel) {
            UserDetailModel(
            avatarUrl = avatarUrl,
            company =  company,
            email = email,
           login = login,
           name = name,
           url = url,
           location = location

            )
        }

    }
}