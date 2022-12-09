package com.llyods.assignment.domain.datamodel

sealed class BaseModelResult<out T : Any> {
    data class OnSuccess<out T : Any>(val data: T) : BaseModelResult<T>()
    data class OnFailure(val throwable: Throwable) : BaseModelResult<Nothing>()
}