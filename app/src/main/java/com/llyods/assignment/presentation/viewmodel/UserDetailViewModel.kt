package com.llyods.assignment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.llyods.assignment.domain.datamodel.BaseModelResult
import com.llyods.assignment.domain.datamodel.UserDetailModel
import com.llyods.assignment.domain.usecase.GetUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
) : ViewModel() {

    private val _userDetail = MutableStateFlow<ViewState<UserDetailModel>>(ViewState.Loading(true))
    val userDetailStateflow get() = _userDetail

    fun getUserDetail(user: String) {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                getUserDetailUseCase(user)
            }.collect {
                when (it) {
                    is ViewState.Loading -> _userDetail.value = ViewState.Loading(false)
                    is ViewState.Failure -> _userDetail.value = it
                    is ViewState.Success -> {
                        it.output.let { artists ->
                            _userDetail.value = ViewState.Success(artists)
                        }
                    }
                }
            }
        }
    }


    suspend fun <T : Any> getViewStateFlowForNetworkCall(ioOperation: suspend () -> Flow<BaseModelResult<T>>) =
        flow {
            emit(ViewState.Loading(true))
            ioOperation().map {
                when (it) {
                    is BaseModelResult.OnSuccess -> ViewState.Success(it.data)
                    is BaseModelResult.OnFailure -> ViewState.Failure(it.throwable)
                }
            }.collect {
                emit(it)
            }
            emit(ViewState.Loading(false))
        }.flowOn(Dispatchers.IO)

}