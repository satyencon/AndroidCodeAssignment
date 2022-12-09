package com.llyods.assignment.domain.repository

import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.datamodel.UserModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getUserList(): Flow<BaseModelResult<List<UserModel>>>
    suspend fun getUserDetail(user: String): Flow<BaseModelResult<UserDetailModel>>
}