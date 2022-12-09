package com.llyods.assignment.data.repository


import com.llyods.assignment.data.mapper.UserDomainMapper
import com.llyods.assignment.data.remote.ApiService
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.datamodel.UserModel
import com.llyods.assignment.domain.repository.AppRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@DelicateCoroutinesApi
class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: UserDomainMapper
): AppRepository {

    override suspend fun getUserList(): Flow<BaseModelResult<List<UserModel>>> {
        return flow {
            try {
                val responseData = apiService.getUserList()
                val userData = mapper.map(responseData)
                emit(BaseModelResult.OnSuccess(userData))
            } catch (e: Exception) {
                emit(BaseModelResult.OnFailure(e))
            }
        }
    }

    override suspend fun getUserDetail(user: String): Flow<BaseModelResult<UserDetailModel>> {
        return flow {
            try {
                val userDetails = apiService.getUserDetails(user)
                val userMappedData = mapper.mapperDetail(userDetails)
                emit(BaseModelResult.OnSuccess(userMappedData))
            } catch (e: Exception) {
                emit(BaseModelResult.OnFailure(e))
            }
        }
    }


}