package com.llyods.assignment.domain.usecase

import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(override val repository: AppRepository):
    IUseDetailCase<String, UserDetailModel>{

    override suspend fun invoke(input: String): Flow<BaseModelResult<UserDetailModel>> {
        return repository.getUserDetail(input)
    }
}