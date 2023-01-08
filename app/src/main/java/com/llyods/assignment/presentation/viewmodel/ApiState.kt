package com.llyods.assignment.presentation.viewmodel


sealed class ApiState<out T : Any> {

    data class Loading(val isLoading: Boolean) : ApiState<Nothing>()
    data class Success<out T : Any>(val output: T) : ApiState<T>()
    data class Failure(val throwable: Throwable) : ApiState<Nothing>()

}