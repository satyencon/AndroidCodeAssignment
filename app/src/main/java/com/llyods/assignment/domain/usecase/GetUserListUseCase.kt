package com.llyods.assignment.domain.usecase

import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    val repository: AppRepository
) {

    suspend operator fun invoke(): Flow<BaseModelResult<List<UserModel>>> {
        return repository.getUserList()
    }


}