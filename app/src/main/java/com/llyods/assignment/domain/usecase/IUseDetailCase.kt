package com.llyods.assignment.domain.usecase


import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

interface IUseDetailCase<in I : Any, out O : Any>  {

    val repository: AppRepository
    suspend operator fun invoke(input: I): Flow<BaseModelResult<O>>
}